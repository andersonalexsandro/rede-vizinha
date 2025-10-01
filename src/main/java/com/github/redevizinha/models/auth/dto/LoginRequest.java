package com.github.redevizinha.models.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest {
    @NotNull
    @NotBlank
    private String usernameOrEmail;
    @NotNull
    private String password;
}
