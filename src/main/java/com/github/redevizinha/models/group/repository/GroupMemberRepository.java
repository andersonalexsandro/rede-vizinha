package com.github.redevizinha.models.group.repository;

import com.github.redevizinha.models.group.entity.GroupMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    boolean existsByGroupIdAndUserId(Long groupId, Long userId);
    Page<GroupMember> findByGroupId(Long groupId, Pageable pageable);
}
