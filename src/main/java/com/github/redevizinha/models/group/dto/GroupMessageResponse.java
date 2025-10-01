package com.github.redevizinha.models.group.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GroupMessageResponse {
    private Long id;
    private Long groupId;
    private Long authorId;
    private String content;
    private LocalDateTime sentAt;    // createdAt
    private boolean edited;
    private LocalDateTime editedAt;
}
