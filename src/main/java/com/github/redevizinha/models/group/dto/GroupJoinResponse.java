package com.github.redevizinha.models.group.dto;

import com.github.redevizinha.commons.Role;
import lombok.Data;

@Data
public class GroupJoinResponse {
    private Long groupId;
    private Long userId;
    private Role role;
}
