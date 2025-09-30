package com.github.redevizinha.models.user.dto;

import java.time.LocalDateTime;
import java.util.Date;

public record UserResponse(
        Long id,
        String name,
        String username,
        String email,
        String phone,
        Date birthDate,
        String photoUrl,
        String bio,
        boolean isPublicProfile,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
){
}
