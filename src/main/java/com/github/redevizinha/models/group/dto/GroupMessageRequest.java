package com.github.redevizinha.models.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GroupMessageRequest {
    @NotBlank(message = "Conteúdo não pode estar vazio")
    @Size(max = 3000, message = "Mensagem pode ter no máximo 3000 caracteres")
    private String content;
}
