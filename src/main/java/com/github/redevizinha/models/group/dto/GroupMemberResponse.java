package com.github.redevizinha.models.group.dto;

import lombok.Data;

@Data
public class GroupMemberResponse {
    private Long userId;
    private String userName;
    private String role;
    private String joinedAt;
}
