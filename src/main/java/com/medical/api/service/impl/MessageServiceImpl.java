package com.medical.api.service.impl;

import com.medical.api.dao.request.MessageCreationRequest;
import com.medical.api.dao.response.MessageResponse;
import com.medical.api.domain.Chat;
import com.medical.api.domain.Message;
import com.medical.api.mapper.MessageMapper;
import com.medical.api.repository.MessageRepository;
import com.medical.api.service.ChatService;
import com.medical.api.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatService chatService;


    @Override
    public List<Message> getAllByChatId(Integer chatId) {
        chatService.ensureUserHaveAccessToChat(chatId);
        return messageRepository.findByPersistentChatId(chatId).stream()
                .map(MessageMapper::toMessage)
                .toList();
    }

    @Override
    public Message createOrUpdateMessage(MessageCreationRequest messageCreationRequest, String answer, LocalDateTime createdQuestionAt, LocalDateTime createdAnswerAt) {
        Chat chat = chatService.ensureUserHaveAccessToChat(messageCreationRequest.getChatId());
        Message message = Message.builder()
                .question(messageCreationRequest.getQuestion())
                .answer(answer)
                .chat(chat)
                .createdQuestionAt(createdQuestionAt)
                .createdAnswerAt(createdAnswerAt)
                .build();
        return MessageMapper.toMessage(messageRepository.save(MessageMapper.toPersistentMessage(message)));
    }

    @Override
    public void deleteMessage(Integer messageId) {
        Integer chatId = messageRepository.findById(messageId).map(m -> m.getPersistentChat().getId()).orElse(null);
        if (chatId == null) {
            return;
        }
        chatService.ensureUserHaveAccessToChat(chatId);
        messageRepository.deleteById(messageId);
    }
}
