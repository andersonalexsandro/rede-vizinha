package com.github.redevizinha.models.connection.repository;

import com.github.redevizinha.models.connection.entity.Connection;
import com.github.redevizinha.models.connection.enums.ConnectionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    @Query("SELECT c FROM Connection c WHERE " +
            "(c.requester.id = :userId OR c.receiver.id = :userId) " +
            "AND c.status = :status")
    Page<Connection> findAllByUserAndStatus(Long userId, ConnectionStatus status, Pageable pageable);

    @Query("SELECT c FROM Connection c WHERE " +
            "((c.requester.id = :userId1 AND c.receiver.id = :userId2) OR " +
            "(c.requester.id = :userId2 AND c.receiver.id = :userId1)) " +
            "AND (:status IS NULL OR c.status = :status)")
    Optional<Connection> findByUsers(Long userId1, Long userId2, ConnectionStatus status);
}
