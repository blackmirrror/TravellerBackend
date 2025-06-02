package ru.blackmirrror.traveller.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.blackmirrror.traveller.models.Message;
import ru.blackmirrror.traveller.services.ChatService;
import ru.blackmirrror.traveller.services.MessageService;
import ru.blackmirrror.traveller.services.OnlineUserTracker;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final OnlineUserTracker onlineUserTracker;
    private final Map<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatService chatService;

    public ChatWebSocketHandler(OnlineUserTracker onlineUserTracker) {
        this.onlineUserTracker = onlineUserTracker;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long userId = extractUserId(session);
        if (userId != null) {
            onlineUserTracker.setOnline(userId);
            userSessions.put(userId, session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long userId = extractUserId(session);
        if (userId != null) {
            onlineUserTracker.setOffline(userId);
            userSessions.remove(userId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(payload);

        String type = node.get("type").asText();

        switch (type) {
            case "message" -> {
                Long chatId = node.get("chatId").asLong();
                Long senderId = node.get("senderId").asLong();
                String text = node.get("text").asText();

                // 1. Сохрани сообщение в базу
                Message saved = messageService.saveMessage(chatId, senderId, text);

                // 2. Рассылай другим участникам чата
                for (Long userId : chatService.getChatParticipantIds(chatId)) {
                    if (!userId.equals(senderId)) {
                        sendToUser(userId, mapper.writeValueAsString(Map.of(
                                "type", "new_message",
                                "chatId", chatId,
                                "message", saved.getText(),
                                "senderId", senderId
                        )));
                    }
                }
            }

            case "mark_as_read" -> {
                Long messageId = node.get("messageId").asLong();
                Long userId = extractUserId(session);
                messageService.markAsRead(messageId);

                // Получаем ID всех участников чата, кроме отправителя
                Long chatId = node.get("chatId").asLong();
                for (Long otherUserId : chatService.getChatParticipantIds(chatId)) {
                    if (!otherUserId.equals(userId)) {
                        sendToUser(otherUserId, mapper.writeValueAsString(Map.of(
                                "type", "message_read",
                                "messageId", messageId,
                                "userId", userId
                        )));
                    }
                }
            }

            case "typing" -> {
                Long chatId = node.get("chatId").asLong();
                Long userId = extractUserId(session);

                // Уведомить всех, что пользователь печатает
                for (Long otherId : chatService.getChatParticipantIds(chatId)) {
                    if (!otherId.equals(userId)) {
                        sendToUser(otherId, mapper.writeValueAsString(Map.of(
                                "type", "typing",
                                "chatId", chatId,
                                "userId", userId
                        )));
                    }
                }
            }
        }
    }

    public void sendToUser(Long userId, String json) throws IOException {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(json));
        }
    }

    private Long extractUserId(WebSocketSession session) {
        String query = session.getUri().getQuery(); // userId=123
        if (query != null && query.startsWith("userId=")) {
            try {
                return Long.parseLong(query.substring("userId=".length()));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
