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
import java.util.Objects;
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

        List<Chat> chats = chatRepository.findChatsByUser(currentUser);
        List<ChatResponseDto> result = new ArrayList<>();

        for (Chat chat : chats) {

            User companion;
            if (Objects.equals(chat.getUser1().getId(), currentUser.getId())) companion = chat.getUser2();
            else companion = chat.getUser1();

            ChatResponseDto dto = new ChatResponseDto();
            dto.setChatId(chat.getId());
            dto.setImageUrl(companion.getPhotoUrl());
//            dto.setImageUrl(chat.getImageUrl());
            dto.setLastMessage(chat.getLastMessageText());
//            dto.setLastMessage(chat.getLastMessage());
            dto.setLastMessageTime(chat.getLastMessageTime());


//            User companion = chat.getUsers().stream()
//                    .filter(u -> !u.getId().equals(userId))
//                    .findFirst()
//                    .orElse(null);

            dto.setTitle(companion.getUsername());
            dto.setOnline(onlineUserTracker.isOnline(companion.getId()));

            int unreadCount = messageRepository.countUnreadMessages(chat.getId(), currentUser);
            dto.setUnreadCount(unreadCount);

            result.add(dto);
        }

        return result;
    }

    public Chat createChat(List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        Chat chat = new Chat();
        chat.setUser1(users.get(0));
        chat.setUser2(users.get(1));
        return chatRepository.save(chat);
    }

    public Chat getChatById(Long id) {
        return chatRepository.findById(id).orElseThrow();
    }

    public void deleteChat(Long id) {
        chatRepository.deleteById(id);
    }

    public List<Long> getChatParticipantIds(Long chatId) {
        Chat chat = chatRepository.findByIdWithUsers(chatId);
        List<Long> userIds = new ArrayList<>();
        if (chat != null) {
            userIds.add(chat.getUser1().getId());
            userIds.add(chat.getUser2().getId());
        }
        return userIds;
    }
}

