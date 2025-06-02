package ru.blackmirrror.traveller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.blackmirrror.traveller.controller.dto.ChatResponseDto;
import ru.blackmirrror.traveller.models.Chat;
import ru.blackmirrror.traveller.services.ChatService;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity<List<ChatResponseDto>> getChats(@RequestParam Long userId) {
        List<ChatResponseDto> chats = chatService.getChatsForUser(userId);
        if (chats == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(chats);
    }

    @PostMapping
    public Chat createChat(@RequestBody List<Long> userIds) {
        return chatService.createChat(userIds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChat(@PathVariable Long id) {
        Chat chat = chatService.getChatById(id);
        if (chat != null) {
            return ResponseEntity.ok(chat);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
    }
}

