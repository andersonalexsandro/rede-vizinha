package com.github.redevizinha.models.bubble.entity;

import com.github.redevizinha.models.group.entity.Group;
import com.github.redevizinha.commons.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "bubbles")
public class Bubble extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double radius; // em metros

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "bubble", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Group> groups = new HashSet<>();
}
