package com.github.redevizinha.models.group.entity;

import com.github.redevizinha.models.user.entity.User;
import com.github.redevizinha.commons.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Getter @Setter
@Table(name = "group_messages")
public class GroupMessage extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // sent_at = createdAt (herdado)
    @Column(nullable = false)
    private boolean edited = false;

    @Column(name = "edited_at")
    private LocalDateTime editedAt;
}
