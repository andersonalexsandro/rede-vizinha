package com.github.redevizinha.models.directMessage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageRequest {

    @NotBlank(message = "O conteúdo da mensagem não pode estar vazio")
    @Size(max = 1000, message = "A mensagem não pode ter mais que 1000 caracteres")
    private String content;
}
