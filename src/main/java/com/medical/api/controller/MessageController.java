package com.medical.api.controller;

import com.medical.api.dao.request.AnswerRequest;
import com.medical.api.dao.request.MessageCreationRequest;
import com.medical.api.dao.response.AnswerResponse;
import com.medical.api.dao.response.MessageResponse;
import com.medical.api.domain.Message;
import com.medical.api.entities.User;
import com.medical.api.exception.WebServerException;
import com.medical.api.mapper.MessageMapper;
import com.medical.api.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final WebClient webClient;

    @PostMapping
    public MessageResponse createMessage(@RequestBody MessageCreationRequest messageCreationRequest, @AuthenticationPrincipal User user) {
        LocalDateTime questionCreationTime = LocalDateTime.now();
        List<Message> messages = messageService.getAllByChatId(messageCreationRequest.getChatId());

        AnswerRequest answerRequest = AnswerRequest.builder()
                .question(messageCreationRequest.getQuestion())
                .history(messages.stream().map(MessageMapper::toHistoryItem).toList())
                .build();

        AnswerResponse answer = webClient.post()
                .uri("/chat")
                .body(Mono.just(answerRequest), AnswerRequest.class)
                .retrieve()
                .bodyToMono(AnswerResponse.class)
                .block();

        LocalDateTime answerCreationTime = LocalDateTime.now();
        if (answer == null) {
            throw new WebServerException();
        }
        return MessageMapper.toResponse(messageService.createOrUpdateMessage(messageCreationRequest, answer.getAnswer(), questionCreationTime, answerCreationTime));
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
