package com.github.redevizinha.models.group.dto;

import lombok.Data;

@Data
public class GroupJoinResponse {
    private Long groupId;
    private Long userId;
    private String role;
}
