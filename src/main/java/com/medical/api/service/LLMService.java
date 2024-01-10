package com.medical.api.service;

import com.medical.api.domain.Message;

import java.util.List;

public interface LLMService {

    String getAnswer(String question, List<Message> history);
}
