package com.medical.api.repository;

import com.medical.api.entities.PersistentMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<PersistentMessage, Integer> {
    List<PersistentMessage> findByPersistentChatId(Integer persistentChatId);
}
