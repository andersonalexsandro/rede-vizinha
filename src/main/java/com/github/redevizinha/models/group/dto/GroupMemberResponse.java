package com.github.redevizinha.models.group.dto;

import com.github.redevizinha.commons.Role;
import lombok.Data;

@Data
public class GroupMemberResponse {
    private Long userId;
    private String userName;
    private Role role;
    private String joinedAt;
}
