package com.github.redevizinha.models.friendship.entity;

import com.github.redevizinha.models.friendship.enums.FriendshipStatus;
import com.github.redevizinha.models.user.entity.User;
import com.github.redevizinha.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "friendships")
public class Friendship extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FriendshipStatus status = FriendshipStatus.PENDING;

    @Temporal(TemporalType.DATE)
    @Column(name = "accepted_at")
    private Date acceptedAt;
}
