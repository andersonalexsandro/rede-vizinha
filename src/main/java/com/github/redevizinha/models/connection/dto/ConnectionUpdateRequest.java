package com.github.redevizinha.models.connection.dto;

import com.github.redevizinha.models.connection.enums.ConnectionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConnectionUpdateRequest {
    @NotNull(message = "Status não pode ser nulo")
    private ConnectionStatus status;
}
