package ru.blackmirrror.traveller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.blackmirrror.traveller.models.Message;
import ru.blackmirrror.traveller.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Message> getMessages(@RequestParam Long chatId) {
        return messageService.getMessagesForChat(chatId);
    }

    @PostMapping
    public Message sendMessage(@RequestParam Long chatId,
                               @RequestParam Long senderId,
                               @RequestBody String content) {
        return messageService.sendMessage(chatId, senderId, content);
    }

    @PostMapping("/read")
    public void markAsRead(@RequestParam Long messageId,
                           @RequestParam Long userId) {
        messageService.markAsRead(messageId);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }
}
