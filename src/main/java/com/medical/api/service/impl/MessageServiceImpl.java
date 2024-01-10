package com.medical.api.service.impl;

import com.medical.api.domain.Chat;
import com.medical.api.domain.Message;
import com.medical.api.entities.User;
import com.medical.api.mapper.MessageMapper;
import com.medical.api.repository.MessageRepository;
import com.medical.api.service.ChatService;
import com.medical.api.service.LLMService;
import com.medical.api.service.MessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final LLMService llmService;

    @Override
    @Transactional
    public Message createMessage(Integer chatId, String question, User user) {
        LocalDateTime questionCreationTime = LocalDateTime.now();
        List<Message> messages = Collections.emptyList();
        if(chatId != null) {
            messages = getAllByChatId(chatId);
        }

        String answer = llmService.getAnswer(question, messages);
        LocalDateTime answerCreationTime = LocalDateTime.now();

        Integer messageChatId = chatId;
        if (messageChatId == null) {
            messageChatId = chatService.createChat(createChatName(answer), user).getChatId();
        }

        return createOrUpdateMessage(messageChatId, question, answer, questionCreationTime, answerCreationTime);
    }

    @Override
    public List<Message> getAllByChatId(Integer chatId) {
        chatService.ensureUserHaveAccessToChat(chatId);
        return messageRepository.findByPersistentChatId(chatId).stream()
                .map(MessageMapper::toMessage)
                .toList();
    }

    @Override
    public Message createOrUpdateMessage(Integer chatId, String question, String answer, LocalDateTime createdQuestionAt, LocalDateTime createdAnswerAt) {
        Chat chat = chatService.ensureUserHaveAccessToChat(chatId);
        Message message = Message.builder()
                .question(question)
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

    private String createChatName(String text) {
        if(text.length() < 42) {
            return text;
        }
        return text.substring(0, 40) + "...";
    }
}
