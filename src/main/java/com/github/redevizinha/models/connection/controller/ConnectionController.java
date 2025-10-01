package com.github.redevizinha.models.connection.controller;

import com.github.redevizinha.models.connection.dto.ConnectionResponse;
import com.github.redevizinha.models.connection.dto.ConnectionUpdateRequest;
import com.github.redevizinha.models.connection.service.ConnectionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/connections")
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping("/{id}")
    public ConnectionResponse sendFriendRequest(@PathVariable Long id) {
        return connectionService.sendFriendRequest(id);
    }

    @GetMapping
    public Page<ConnectionResponse> getFriends(Pageable pageable) {
        return connectionService.getFriends(pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFriend(@PathVariable Long id) {
        connectionService.removeFriend(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/block")
    public ConnectionResponse blockUser(@PathVariable Long id) {
        return connectionService.blockUser(id);
    }

    @PutMapping("/{id}")
    public ConnectionResponse updateConnection(
            @PathVariable Long id,
            @RequestBody @Valid ConnectionUpdateRequest request
    ) {
        return connectionService.updateConnection(id, request);
    }
}
