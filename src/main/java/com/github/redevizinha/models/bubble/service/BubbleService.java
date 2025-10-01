package com.github.redevizinha.models.bubble.service;

import com.github.redevizinha.models.bubble.dto.BubbleRequest;
import com.github.redevizinha.models.bubble.dto.BubbleResponse;
import com.github.redevizinha.models.bubble.entity.Bubble;
import com.github.redevizinha.models.bubble.repository.BubbleRepository;
import com.github.redevizinha.models.user.entity.User;
import com.github.redevizinha.models.user.repository.UserRepository;
import com.github.redevizinha.security.UserContextProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BubbleService {

    private final BubbleRepository bubbleRepository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final UserContextProvider userContextProvider;

    public BubbleResponse createBubble(BubbleRequest request) {
        Bubble bubble = mapper.map(request, Bubble.class);
        bubble = bubbleRepository.save(bubble);
        return mapper.map(bubble, BubbleResponse.class);
    }

    public Page<BubbleResponse> getAllBubbles(Pageable pageable) {
        return bubbleRepository.findAll(pageable)
                .map(bubble -> mapper.map(bubble, BubbleResponse.class));
    }

    public BubbleResponse getBubbleById(Long id) {
        Bubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bubble not found with id: " + id));
        return mapper.map(bubble, BubbleResponse.class);
    }

    public BubbleResponse updateBubble(Long id, BubbleRequest request) {
        Bubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bubble not found with id: " + id));

        mapper.map(request, bubble);
        Bubble updated = bubbleRepository.save(bubble);

        return mapper.map(updated, BubbleResponse.class);
    }

    public void deleteBubble(Long id) {
        Bubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bubble not found with id: " + id));
        bubbleRepository.delete(bubble);
    }
}
