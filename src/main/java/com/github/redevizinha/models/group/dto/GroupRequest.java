package com.github.redevizinha.models.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class GroupRequest {
    @NotNull(message = "A bolha é obrigatória")
    private Long bubbleId;

    @NotBlank(message = "O nome do grupo é obrigatório")
    @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
    private String name;

    @Size(max = 500, message = "A descrição pode ter no máximo 500 caracteres")
    private String description;

    private List<@Size(min = 1, max = 40) String> tags;
}
