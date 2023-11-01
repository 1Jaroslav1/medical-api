package com.medical.api.mapper;

import com.medical.api.dao.response.MessageResponse;
import com.medical.api.domain.HistoryItem;
import com.medical.api.domain.Message;
import com.medical.api.entities.PersistentMessage;

public class MessageMapper {
    public static Message toMessage(PersistentMessage persistentMessage) {
        if (persistentMessage == null) {
            return null;
        }
        return Message.builder()
                .question(persistentMessage.getQuestion())
                .answer(persistentMessage.getAnswer())
                .chat(ChatMapper.toChat(persistentMessage.getPersistentChat()))
                .createdQuestionAt(persistentMessage.getCreatedQuestionAt())
                .createdAnswerAt(persistentMessage.getCreatedAnswerAt())
                .build();
    }

    public static MessageResponse toResponse(PersistentMessage persistentMessage) {
        if (persistentMessage == null) {
            return null;
        }
        return MessageResponse.builder()
                .question(persistentMessage.getQuestion())
                .answer(persistentMessage.getAnswer())
                .createdQuestionAt(persistentMessage.getCreatedQuestionAt())
                .createdAnswerAt(persistentMessage.getCreatedAnswerAt())
                .build();
    }

    public static MessageResponse toResponse(Message message) {
        if (message == null) {
            return null;
        }
        return MessageResponse.builder()
                .question(message.getQuestion())
                .answer(message.getAnswer())
                .createdQuestionAt(message.getCreatedQuestionAt())
                .createdAnswerAt(message.getCreatedAnswerAt())
                .build();
    }

    public static PersistentMessage toPersistentMessage(Message message) {
        return PersistentMessage.builder()
                .question(message.getQuestion())
                .answer(message.getAnswer())
                .persistentChat(ChatMapper.toPersistentChat(message.getChat()))
                .createdQuestionAt(message.getCreatedQuestionAt())
                .createdAnswerAt(message.getCreatedAnswerAt())
                .build();
    }

    public static HistoryItem toHistoryItem(Message message) {
        return HistoryItem.builder()
                .human_message(message.getQuestion())
                .ai_message(message.getAnswer())
                .build();
    }
}
