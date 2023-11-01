package com.medical.api.service;

import com.medical.api.dao.request.MessageCreationRequest;
import com.medical.api.dao.response.MessageResponse;
import com.medical.api.domain.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {
    List<Message> getAllByChatId(Integer chatId);

    Message createOrUpdateMessage(MessageCreationRequest messageCreationRequest, String answer, LocalDateTime createdQuestionAt, LocalDateTime createdAnswerAt);

    void deleteMessage(Integer messageId);
}
