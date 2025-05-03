package ru.blackmirrror.traveller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.blackmirrror.traveller.controller.dto.ChatResponseDto;
import ru.blackmirrror.traveller.models.Chat;
import ru.blackmirrror.traveller.models.User;
import ru.blackmirrror.traveller.repositories.ChatRepository;
import ru.blackmirrror.traveller.repositories.MessageRepository;
import ru.blackmirrror.traveller.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OnlineUserTracker onlineUserTracker;

    public List<ChatResponseDto> getChatsForUser(Long userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Chat> chats = chatRepository.findByUsers(currentUser);
        List<ChatResponseDto> result = new ArrayList<>();

        for (Chat chat : chats) {
            ChatResponseDto dto = new ChatResponseDto();
            dto.setChatId(chat.getId());
            dto.setImageUrl(chat.getImageUrl());
            dto.setLastMessage(chat.getLastMessage());
            dto.setLastMessageTime(chat.getLastMessageTime());

            User companion = chat.getUsers().stream()
                    .filter(u -> !u.getId().equals(userId))
                    .findFirst()
                    .orElse(null);

            if (companion != null) {
                dto.setTitle(companion.getUsername());
                dto.setOnline(onlineUserTracker.isOnline(companion.getId()));
            }

            int unreadCount = messageRepository.countUnreadMessages(chat.getId(), currentUser);
            dto.setUnreadCount(unreadCount);

            result.add(dto);
        }

        return result;
    }

    public Chat createChat(List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        Chat chat = new Chat();
        chat.setUsers(users);
        return chatRepository.save(chat);
    }

    public Optional<Chat> getChatById(Long id) {
        return chatRepository.findById(id);
    }

    public void deleteChat(Long id) {
        chatRepository.deleteById(id);
    }

    public List<Long> getChatParticipantIds(Long chatId) {
        Chat chat = chatRepository.findByIdWithUsers(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        return chat.getUsers()
                .stream()
                .map(User::getId)
                .toList();
    }
}

