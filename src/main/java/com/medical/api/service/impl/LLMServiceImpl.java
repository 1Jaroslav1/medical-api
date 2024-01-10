package com.medical.api.service.impl;

import com.medical.api.dao.request.AnswerRequest;
import com.medical.api.dao.response.AnswerResponse;
import com.medical.api.domain.Message;
import com.medical.api.exception.WebServerException;
import com.medical.api.mapper.MessageMapper;
import com.medical.api.service.LLMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LLMServiceImpl implements LLMService {

    private static final String CHAT_ENDPOINT = "/chat";

    private final WebClient webClient;

    @Override
    public String getAnswer(String question, List<Message> history) {
        AnswerRequest answerRequest = AnswerRequest.builder()
                .question(question)
                .history(history.stream().map(MessageMapper::toHistoryItem).toList())
                .build();

        AnswerResponse answer = webClient.post()
                .uri(CHAT_ENDPOINT)
                .body(Mono.just(answerRequest), AnswerRequest.class)
                .retrieve()
                .bodyToMono(AnswerResponse.class)
                .block();

        if (answer == null) {
            throw new WebServerException();
        }
        return answer.getAnswer().replaceAll("\n", "").trim();
    }

}
