package com.github.redevizinha.models.bubble.repository;

import com.github.redevizinha.models.bubble.entity.Bubble;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BubbleRepository extends JpaRepository<Bubble, Long> {
}
