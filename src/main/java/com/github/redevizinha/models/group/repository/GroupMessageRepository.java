package com.github.redevizinha.models.group.repository;

import com.github.redevizinha.models.group.entity.GroupMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long> {
    Page<GroupMessage> findByGroupIdOrderByCreatedAtAsc(Long groupId, Pageable pageable);
}
