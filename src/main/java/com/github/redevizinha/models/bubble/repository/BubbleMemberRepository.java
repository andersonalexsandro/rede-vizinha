package com.github.redevizinha.models.bubble.repository;

import com.github.redevizinha.models.bubble.entity.BubbleMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BubbleMemberRepository extends JpaRepository<BubbleMember, Long> {

    Page<BubbleMember> findByBubbleId(Long bubbleId, Pageable pageable);

    Optional<BubbleMember> findByBubbleIdAndUserId(Long bubbleId, Long userId);

    @Query("SELECT bm FROM BubbleMember bm " +
            "JOIN bm.user u " +
            "WHERE bm.bubble.id = :bubbleId AND u.id IN :friendIds")
    Page<BubbleMember> findFriendsInBubble(Long bubbleId, List<Long> friendIds, Pageable pageable);
}
