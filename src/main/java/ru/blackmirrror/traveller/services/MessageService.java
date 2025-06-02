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
        return messageRepository.findByChatIdOrderByDateCreateAsc(chatId);
    }

    public Message sendMessage(Long chatId, Long senderId, String content) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setText(content);
        message.setDateCreate(System.currentTimeMillis());
        message.setRead(true); // автоматически прочитано отправителем

        chat.setLastMessageText(content);
        chat.setLastMessageTime(message.getDateCreate());
        chatRepository.save(chat);

        return messageRepository.save(message);
    }

    public void markAsRead(Long messageId) {
        Message message = messageRepository.findMessageById(messageId);
        if (message != null) {
            message.setRead(true);
            messageRepository.save(message);
        }
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
        message.setText(text);
        message.setDateCreate(System.currentTimeMillis());
        message.setRead(false); // Никто ещё не прочитал

        Message saved = messageRepository.save(message);

        // обновить последнее сообщение и время в чате
        chat.setLastMessageText(text);
        chat.setLastMessageTime(saved.getDateCreate());
        chatRepository.save(chat);

        return saved;
    }
}

