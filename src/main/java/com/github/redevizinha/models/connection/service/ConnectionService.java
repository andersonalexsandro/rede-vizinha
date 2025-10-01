package com.github.redevizinha.models.connection.service;

import com.github.redevizinha.models.connection.dto.ConnectionResponse;
import com.github.redevizinha.models.connection.dto.ConnectionUpdateRequest;
import com.github.redevizinha.models.connection.entity.Connection;
import com.github.redevizinha.models.connection.enums.ConnectionStatus;
import com.github.redevizinha.models.connection.repository.ConnectionRepository;
import com.github.redevizinha.models.user.entity.User;
import com.github.redevizinha.models.user.repository.UserRepository;
import com.github.redevizinha.security.UserContextProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;
    private final UserContextProvider userContextProvider;
    private final ModelMapper mapper;

    private ConnectionResponse toResponse(Connection connection) {
        ConnectionResponse dto = mapper.map(connection, ConnectionResponse.class);
        dto.setRequesterId(connection.getRequester().getId());
        dto.setRequesterName(connection.getRequester().getName());
        dto.setReceiverId(connection.getReceiver().getId());
        dto.setReceiverName(connection.getReceiver().getName());
        return dto;
    }

    public ConnectionResponse sendFriendRequest(Long receiverId) {
        Long requesterId = userContextProvider.getCurrentUserId();

        if(requesterId.equals(receiverId)) {
            throw new IllegalArgumentException("You cannot send a friend request to yourself");
        }

        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new EntityNotFoundException("Requester not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        Connection connection = new Connection();
        connection.setRequester(requester);
        connection.setReceiver(receiver);
        connection.setStatus(ConnectionStatus.PENDING);

        return toResponse(connectionRepository.save(connection));
    }

    public Page<ConnectionResponse> getFriends(Pageable pageable) {
        Long userId = userContextProvider.getCurrentUserId();
        return connectionRepository.findAllByUserAndStatus(userId, ConnectionStatus.ACCEPTED, pageable)
                .map(this::toResponse);
    }

    public void removeFriend(Long friendId) {
        Long userId = userContextProvider.getCurrentUserId();

        if(userId.equals(friendId)) {
            throw new IllegalArgumentException("You cannot remove yourself as a friend");
        }

        Connection connection = connectionRepository
                .findByUsers(userId, friendId, ConnectionStatus.ACCEPTED)
                .orElseThrow(() -> new EntityNotFoundException("Connection not found"));

        connectionRepository.delete(connection);
    }

    public ConnectionResponse blockUser(Long receiverId) {
        Long requesterId = userContextProvider.getCurrentUserId();

        if(requesterId.equals(receiverId)) {
            throw new IllegalArgumentException("You cannot block yourself");
        }

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

        return toResponse(connectionRepository.save(connection));
    }

    public ConnectionResponse updateConnection(Long connectionId, ConnectionUpdateRequest request) {
        Long currentUserId = userContextProvider.getCurrentUserId();

        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new EntityNotFoundException("Connection not found"));

        boolean isRequester = connection.getRequester().getId().equals(currentUserId);
        boolean isReceiver = connection.getReceiver().getId().equals(currentUserId);

        ConnectionStatus currentStatus = connection.getStatus();
        ConnectionStatus newStatus = request.getStatus();

        if (currentStatus == ConnectionStatus.PENDING) {
            if (!isReceiver) {
                throw new SecurityException("Somente o receptor pode responder a esta solicitação.");
            }
            if (newStatus == ConnectionStatus.PENDING) {
                throw new IllegalStateException("Não é permitido manter a conexão como pendente.");
            }
        }

        if (currentStatus == ConnectionStatus.ACCEPTED) {
            if (!(isRequester || isReceiver)) {
                throw new SecurityException("Você não tem permissão para atualizar esta conexão.");
            }
            if (newStatus == ConnectionStatus.PENDING) {
                throw new IllegalStateException("Não é permitido reverter uma conexão aceita para pendente.");
            }
        }

        if ((currentStatus == ConnectionStatus.REJECTED || currentStatus == ConnectionStatus.BLOCKED)
                && newStatus == ConnectionStatus.PENDING) {
            throw new IllegalStateException("Não é permitido reabrir uma conexão para pendente.");
        }

        connection.setStatus(newStatus);

        if (newStatus == ConnectionStatus.ACCEPTED) {
            connection.setAcceptedAt(new Date());
        }

        return toResponse(connectionRepository.save(connection));
    }

}