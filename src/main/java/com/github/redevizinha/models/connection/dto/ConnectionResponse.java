package com.github.redevizinha.models.connection.dto;

import com.github.redevizinha.models.connection.enums.ConnectionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionResponse {
    private Long id;
    private Long requesterId;
    private String requesterName;
    private Long receiverId;
    private String receiverName;
    private ConnectionStatus status;
    private Date acceptedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
