package com.github.redevizinha.models.bubble.service;

import com.github.redevizinha.models.bubble.dto.BubbleMemberResponse;
import com.github.redevizinha.models.bubble.entity.Bubble;
import com.github.redevizinha.models.bubble.entity.BubbleMember;
import com.github.redevizinha.models.bubble.repository.BubbleMemberRepository;
import com.github.redevizinha.models.bubble.repository.BubbleRepository;
import com.github.redevizinha.models.connection.entity.Connection;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BubbleMemberService {

    private final BubbleRepository bubbleRepository;
    private final BubbleMemberRepository bubbleMemberRepository;
    private final UserRepository userRepository;
    private final ConnectionRepository connectionRepository;
    private final UserContextProvider userContextProvider;
    private final ModelMapper mapper;

    public Page<BubbleMemberResponse> listUsersInBubble(Long bubbleId, Pageable pageable) {
        return bubbleMemberRepository.findByBubbleId(bubbleId, pageable)
                .map(member -> mapper.map(member, BubbleMemberResponse.class));
    }

    public BubbleMemberResponse joinBubble(Long bubbleId) {
        Long userId = userContextProvider.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Bubble bubble = bubbleRepository.findById(bubbleId)
                .orElseThrow(() -> new EntityNotFoundException("Bubble not found"));

        BubbleMember member = bubbleMemberRepository.findByBubbleIdAndUserId(bubbleId, userId)
                .orElseGet(() -> {
                    BubbleMember bm = new BubbleMember();
                    bm.setUser(user);
                    bm.setBubble(bubble);
                    return bm;
                });

        member.setJoinedAt(LocalDateTime.now());
        member.setLeftAt(null);

        BubbleMember saved = bubbleMemberRepository.save(member);
        return mapper.map(saved, BubbleMemberResponse.class);
    }

    public Page<BubbleMemberResponse> getFriendsInBubble(Long bubbleId, Pageable pageable) {
        Long userId = userContextProvider.getCurrentUserId();

        Page<Connection> connections = connectionRepository.findAllByUserAndStatus(
                userId, com.github.redevizinha.models.connection.enums.ConnectionStatus.ACCEPTED, pageable
        );

        List<Long> friendIds = connections.getContent().stream()
                .map(conn -> conn.getRequester().getId().equals(userId)
                        ? conn.getReceiver().getId()
                        : conn.getRequester().getId())
                .toList();

        return bubbleMemberRepository.findFriendsInBubble(bubbleId, friendIds, pageable)
                .map(member -> mapper.map(member, BubbleMemberResponse.class));
    }

    public String notifyFriends(Long bubbleId) {
        Long userId = userContextProvider.getCurrentUserId();
        return "Notificação enviada aos amigos do usuário " + userId + " na bolha " + bubbleId;
    }
}
