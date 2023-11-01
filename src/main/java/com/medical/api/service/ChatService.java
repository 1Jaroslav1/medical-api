package com.medical.api.service;

import com.medical.api.domain.Chat;
import com.medical.api.entities.User;

import java.util.List;

public interface ChatService {

    Chat getChatById(Integer chatId, User user);

    List<Chat> getChatByUser(User user);

    Chat createChat(String chatName, User user);

    void deleteChat(Integer chatId);

    Chat ensureUserHaveAccessToChat(Integer chatId);
}
