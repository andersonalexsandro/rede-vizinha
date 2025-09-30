package com.github.redevizinha.models.connection.entity;

import com.github.redevizinha.models.connection.enums.ConnectionStatus;
import com.github.redevizinha.models.user.entity.User;
import com.github.redevizinha.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "connections")
public class Connection extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ConnectionStatus status = ConnectionStatus.PENDING;

    @Temporal(TemporalType.DATE)
    @Column(name = "accepted_at")
    private Date acceptedAt;
}
