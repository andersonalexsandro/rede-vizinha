package com.github.redevizinha.models.bubble.entity;

import com.github.redevizinha.models.user.entity.User;
import com.github.redevizinha.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "bubble_members")
public class BubbleMember extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "bubble_id", nullable = false)
    private Bubble bubble;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "left_at")
    private LocalDateTime leftAt;

    @Column(length = 50)
    private String role = "MEMBER"; // padrão: membro normal
}
