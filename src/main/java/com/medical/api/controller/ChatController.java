package com.medical.api.controller;

import com.medical.api.dao.request.ChatCreationRequest;
import com.medical.api.domain.Chat;
import com.medical.api.entities.User;
import com.medical.api.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public Chat createChat(@RequestBody ChatCreationRequest chatCreationRequest, @AuthenticationPrincipal User user) {
        return chatService.createChat(chatCreationRequest.getChatName(), user);
    }

    @GetMapping("/{chatId}")
    public Chat getChatById(@PathVariable Integer chatId, @AuthenticationPrincipal User user) {
        return chatService.getChatById(chatId, user);
    }

    @GetMapping("/all")
    public List<Chat> getAll(@AuthenticationPrincipal User user) {
        return chatService.getChatByUser(user);
    }

    @DeleteMapping("/{chatId}")
    public void deleteChatById(@PathVariable Integer chatId, @AuthenticationPrincipal User user) {
        chatService.deleteChat(chatId);
    }
}
