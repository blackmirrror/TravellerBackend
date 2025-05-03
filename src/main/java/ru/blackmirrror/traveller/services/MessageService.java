package ru.blackmirrror.traveller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.blackmirrror.traveller.models.Chat;
import ru.blackmirrror.traveller.models.Message;
import ru.blackmirrror.traveller.models.User;
import ru.blackmirrror.traveller.repositories.ChatRepository;
import ru.blackmirrror.traveller.repositories.MessageRepository;
import ru.blackmirrror.traveller.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Message> getMessagesForChat(Long chatId) {
        return messageRepository.findByChatIdOrderByTimestampAsc(chatId);
    }

    public Message sendMessage(Long chatId, Long senderId, String content) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setContent(content);
        message.setTimestamp(System.currentTimeMillis());
        message.setReadBy(new HashSet<>(List.of(sender))); // автоматически прочитано отправителем

        chat.setLastMessage(content);
        chat.setLastMessageTime(message.getTimestamp());
        chatRepository.save(chat);

        return messageRepository.save(message);
    }

    public void markAsRead(Long messageId, Long userId) {
        Message message = messageRepository.findMessageWithReadBy(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        message.getReadBy().add(user);
        messageRepository.save(message);
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public Message saveMessage(Long chatId, Long senderId, String text) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setContent(text);
        message.setTimestamp(System.currentTimeMillis());
        message.setReadBy(new HashSet<>()); // Никто ещё не прочитал

        Message saved = messageRepository.save(message);

        // обновить последнее сообщение и время в чате
        chat.setLastMessage(text);
        chat.setLastMessageTime(saved.getTimestamp());
        chatRepository.save(chat);

        return saved;
    }
}

