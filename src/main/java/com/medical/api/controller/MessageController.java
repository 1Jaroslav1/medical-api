package com.medical.api.controller;

import com.medical.api.dao.request.MessageCreationRequest;
import com.medical.api.dao.response.MessageResponse;
import com.medical.api.domain.Message;
import com.medical.api.entities.User;
import com.medical.api.mapper.MessageMapper;
import com.medical.api.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public MessageResponse createMessage(@RequestBody MessageCreationRequest messageCreationRequest, @AuthenticationPrincipal User user) {
        Message message = messageService.createMessage(messageCreationRequest.getChatId(), messageCreationRequest.getQuestion(), user);
        return MessageMapper.toResponse(message);
    }

    @GetMapping("/all/{chatId}")
    public List<MessageResponse> getMessageById(@PathVariable Integer chatId) {
        return messageService.getAllByChatId(chatId).stream().map(MessageMapper::toResponse).toList();
    }

    @DeleteMapping("/{messageId}")
    public void deleteMessage(@PathVariable Integer messageId, @AuthenticationPrincipal User user) {
        messageService.deleteMessage(messageId);
    }
}
