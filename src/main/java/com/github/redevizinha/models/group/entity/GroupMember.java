package com.github.redevizinha.models.group.entity;

import com.github.redevizinha.commons.Role;
import com.github.redevizinha.models.user.entity.User;
import com.github.redevizinha.commons.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Getter @Setter
@Table(name = "group_members")
public class GroupMember extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();

    @Column(name = "left_at")
    private LocalDateTime leftAt;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.MEMBER;
}
