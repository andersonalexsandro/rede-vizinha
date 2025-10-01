package com.github.redevizinha.models.directMessage.repository;

import com.github.redevizinha.models.directMessage.entity.DirectMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
    @Query("SELECT m FROM DirectMessage m " +
            "WHERE (m.sender.id = :userId1 AND m.receiver.id = :userId2) " +
            "   OR (m.sender.id = :userId2 AND m.receiver.id = :userId1) " +
            "ORDER BY m.createdAt ASC")
    Page<DirectMessage> findConversation(Long userId1, Long userId2, Pageable pageable);
}
