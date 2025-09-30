package com.github.redevizinha.models.user.dto;

import jakarta.validation.constraints.*;
import java.util.Date;

public record UserCreateRequest(

        @NotBlank(message = "O nome não pode estar vazio")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String name,

        @NotBlank(message = "O username não pode estar vazio")
        @Size(min = 3, max = 50, message = "O username deve ter entre 3 e 50 caracteres")
        String username,

        @NotBlank(message = "O email não pode estar vazio")
        @Email(message = "Email deve ser válido")
        String email,

        @NotBlank(message = "A senha não pode estar vazia")
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
        String password,

        @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Telefone deve conter entre 10 e 15 dígitos (pode começar com +)")
        String phone,

        @Past(message = "A data de nascimento deve estar no passado")
        Date birthDate,

        @Size(max = 255, message = "A URL da foto não pode ter mais que 255 caracteres")
        String photoUrl,

        @Size(max = 500, message = "A bio pode ter no máximo 500 caracteres")
        String bio,

        boolean isPublicProfile
) {}
