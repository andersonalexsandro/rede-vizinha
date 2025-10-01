package com.github.redevizinha.models.bubble.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BubbleRequest {

    @NotBlank(message = "O nome da bolha é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String name;

    @Size(max = 500, message = "A descrição pode ter no máximo 500 caracteres")
    private String description;

    @NotNull(message = "A latitude é obrigatória")
    private Double latitude;

    @NotNull(message = "A longitude é obrigatória")
    private Double longitude;

    @NotNull(message = "O raio é obrigatório")
    private Double radius;
}
