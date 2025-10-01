package com.github.redevizinha.models.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPutRequest {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String name;

    @NotBlank(message = "O username é obrigatório")
    @Size(min = 3, max = 50, message = "O username deve ter entre 3 e 50 caracteres")
    private String username;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(
            regexp = "\\+?[0-9]{10,15}",
            message = "Telefone deve conter entre 10 e 15 dígitos (pode começar com +)"
    )
    private String phone;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    private String password;

    @NotBlank(message = "A URL da foto é obrigatória")
    @Size(max = 255, message = "A URL da foto não pode ter mais que 255 caracteres")
    private String photoUrl;

    @NotBlank(message = "A bio é obrigatória")
    @Size(max = 500, message = "A bio pode ter no máximo 500 caracteres")
    private String bio;

    @NotNull(message = "A visibilidade do perfil é obrigatória")
    private Boolean isPublicProfile;
}
