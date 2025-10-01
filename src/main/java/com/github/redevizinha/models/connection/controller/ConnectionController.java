package com.github.redevizinha.models.connection.controller;

import com.github.redevizinha.models.connection.entity.Connection;
import com.github.redevizinha.models.connection.service.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/connections")
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping("/{id}")
    public ResponseEntity<Connection> sendFriendRequest(@PathVariable Long id) {
        return ResponseEntity.ok(connectionService.sendFriendRequest(id));
    }

    @GetMapping
    public ResponseEntity<List<Connection>> getFriends() {
        return ResponseEntity.ok(connectionService.getFriends());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFriend(@PathVariable Long id) {
        connectionService.removeFriend(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<Connection> blockUser(@PathVariable Long id) {
        return ResponseEntity.ok(connectionService.blockUser(id));
    }
}
