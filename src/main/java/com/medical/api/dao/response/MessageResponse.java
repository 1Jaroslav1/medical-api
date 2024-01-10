package com.medical.api.dao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Integer chatId;
    private String question;
    private String answer;
    private LocalDateTime createdQuestionAt;
    private LocalDateTime createdAnswerAt;
}
