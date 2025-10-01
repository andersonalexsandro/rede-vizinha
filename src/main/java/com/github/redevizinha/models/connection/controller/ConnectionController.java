package com.github.redevizinha.models.connection.controller;

import com.github.redevizinha.models.connection.entity.Connection;
import com.github.redevizinha.models.connection.service.ConnectionService;
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
    public Connection sendFriendRequest(@PathVariable Long id) {
        return connectionService.sendFriendRequest(id);
    }

    @GetMapping
    public Page<Connection> getFriends(Pageable pageable) {
        return connectionService.getFriends(pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFriend(@PathVariable Long id) {
        connectionService.removeFriend(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/block")
    public Connection blockUser(@PathVariable Long id) {
        return connectionService.blockUser(id);
    }
}
