package com.medical.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String question;
    private String answer;
    private Chat chat;
    private LocalDateTime createdQuestionAt;
    private LocalDateTime createdAnswerAt;
}
