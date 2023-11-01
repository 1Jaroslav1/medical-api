package com.medical.api.service.impl;

import com.medical.api.domain.Chat;
import com.medical.api.entities.User;
import com.medical.api.exception.ChatNotFoundException;
import com.medical.api.mapper.ChatMapper;
import com.medical.api.repository.ChatRepository;
import com.medical.api.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Chat getChatById(Integer chatId, User user) {
        return ChatMapper.toChat(chatRepository.findByIdAndCreatorId(chatId, user.getId()));
    }

    @Override
    public List<Chat> getChatByUser(User user) {
        return chatRepository.findAllByCreatorId(user.getId())
                .stream()
                .map(ChatMapper::toChat)
                .toList();
    }

    @Override
    public Chat createChat(String chatName, User user) {
        Chat chat = Chat.builder()
                .name(chatName)
                .createdAt(LocalDateTime.now())
                .build();
        if (chat == null) {
            return null;
        }
        return ChatMapper.toChat(chatRepository.save(ChatMapper.toPersistentChat(chat)));
    }

    @Override
    public void deleteChat(Integer chatId) {
        chatRepository.deleteById(chatId);
    }

    @Override
    public Chat ensureUserHaveAccessToChat(Integer chatId) {
        User user = UserReadService.getCurrentUser();
        Chat chat = getChatById(chatId, user);
        if (chat == null) {
            throw new ChatNotFoundException();
        }
        return chat;
    }
}
