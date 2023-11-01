package com.medical.api.repository;

import com.medical.api.entities.PersistentChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<PersistentChat, Integer> {
    List<PersistentChat> findAllByCreatorId(Integer creatorId);

    PersistentChat findByIdAndCreatorId(Integer id, Integer creatorId);

}
