package com.medical.api.service;

import com.medical.api.dao.request.MessageCreationRequest;
import com.medical.api.dao.response.MessageResponse;
import com.medical.api.domain.Message;
import com.medical.api.entities.User;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {

    Message createMessage(Integer chatId, String question, User user);

    List<Message> getAllByChatId(Integer chatId);

    Message createOrUpdateMessage(Integer chatId, String question, String answer, LocalDateTime createdQuestionAt, LocalDateTime createdAnswerAt);

    void deleteMessage(Integer messageId);
}
