package com.github.redevizinha.models.bubble.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BubbleResponse {
    private Long id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private Double radius;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
