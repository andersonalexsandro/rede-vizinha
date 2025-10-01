package com.github.redevizinha.models.group.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GroupResponse {
    private Long id;
    private Long bubbleId;
    private Long creatorId;
    private String name;
    private String description;
    private boolean closed;
    private LocalDateTime lastActivity;
    private LocalDateTime createdAt;
    private List<String> tags;
}
