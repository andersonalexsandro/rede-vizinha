package com.github.redevizinha.models.user.controller;

import com.github.redevizinha.models.user.dto.*;
import com.github.redevizinha.models.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> putUser(
            @PathVariable Long id,
            @Valid @RequestBody UserPutRequest request
    ) {
        return ResponseEntity.ok(userService.putUser(id, request));
    }

    @PatchMapping("/{id}/privacy")
    public ResponseEntity<UserResponse> updatePrivacy(
            @PathVariable Long id,
            @Valid @RequestBody UserPrivacyUpdate request
    ) {
        return ResponseEntity.ok(userService.updatePrivacy(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
