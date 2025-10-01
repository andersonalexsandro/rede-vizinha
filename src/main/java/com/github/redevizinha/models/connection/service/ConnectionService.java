package com.github.redevizinha.models.connection.service;

import com.github.redevizinha.models.connection.entity.Connection;
import com.github.redevizinha.models.connection.enums.ConnectionStatus;
import com.github.redevizinha.models.connection.repository.ConnectionRepository;
import com.github.redevizinha.models.user.entity.User;
import com.github.redevizinha.models.user.repository.UserRepository;
import com.github.redevizinha.security.UserContextProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;
    private final UserContextProvider userContextProvider;

    public Connection sendFriendRequest(Long receiverId) {
        Long requesterId = userContextProvider.getCurrentUserId();

        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new EntityNotFoundException("Requester not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        Connection connection = new Connection();
        connection.setRequester(requester);
        connection.setReceiver(receiver);
        connection.setStatus(ConnectionStatus.PENDING);

        return connectionRepository.save(connection);
    }

    public List<Connection> getFriends() {
        Long userId = userContextProvider.getCurrentUserId();
        return connectionRepository.findAllByUserAndStatus(userId, ConnectionStatus.ACCEPTED);
    }

    public void removeFriend(Long friendId) {
        Long userId = userContextProvider.getCurrentUserId();

        Connection connection = connectionRepository
                .findByUsers(userId, friendId, ConnectionStatus.ACCEPTED)
                .orElseThrow(() -> new EntityNotFoundException("Connection not found"));

        connectionRepository.delete(connection);
    }

    public Connection blockUser(Long receiverId) {
        Long requesterId = userContextProvider.getCurrentUserId();

        Connection connection = connectionRepository
                .findByUsers(requesterId, receiverId, null)
                .orElseGet(() -> {
                    Connection newConn = new Connection();
                    newConn.setRequester(userRepository.findById(requesterId)
                            .orElseThrow(() -> new EntityNotFoundException("Requester not found")));
                    newConn.setReceiver(userRepository.findById(receiverId)
                            .orElseThrow(() -> new EntityNotFoundException("Receiver not found")));
                    return newConn;
                });

        connection.setStatus(ConnectionStatus.BLOCKED);
        connection.setAcceptedAt(new Date());

        return connectionRepository.save(connection);
    }
}
