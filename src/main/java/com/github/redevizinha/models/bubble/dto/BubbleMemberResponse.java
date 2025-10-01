package com.github.redevizinha.models.bubble.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BubbleMemberResponse {
    private Long id;
    private Long userId;
    private Long bubbleId;
    private String role;
    private LocalDateTime leftAt;
}
