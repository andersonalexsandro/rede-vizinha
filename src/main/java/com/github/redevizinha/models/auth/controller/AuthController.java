package com.github.redevizinha.models.auth.controller;

import com.github.redevizinha.models.auth.dto.LoginRequest;
import com.github.redevizinha.models.auth.dto.LoginResponse;
import com.github.redevizinha.models.auth.service.AuthService;
import com.github.redevizinha.models.user.dto.UserRequest;
import com.github.redevizinha.models.user.dto.UserResponse;
import com.github.redevizinha.models.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody @Valid UserRequest userRequest){
        return userService.createUser(userRequest);
    }
}
