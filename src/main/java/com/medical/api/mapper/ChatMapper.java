package com.medical.api.mapper;

import com.medical.api.domain.Chat;
import com.medical.api.entities.PersistentChat;
import com.medical.api.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Optional;

public class ChatMapper {
    public static Chat toChat(PersistentChat persistentChat) {
        if (persistentChat == null) {
            return null;
        }
        return Chat.builder()
                .chatId(persistentChat.getId())
                .name(persistentChat.getName())
                .createdAt(persistentChat.getCreatedAt())
                .build();
    }

    public static PersistentChat toPersistentChat(Chat chat) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return PersistentChat.builder()
                .id(chat.getChatId())
                .name(chat.getName())
                .creator((User) auth.getPrincipal())
                .createdAt(chat.getCreatedAt())
                .build();
    }
}
