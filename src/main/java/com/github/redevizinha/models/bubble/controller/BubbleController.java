package com.github.redevizinha.models.bubble.controller;

import com.github.redevizinha.models.bubble.dto.BubbleMemberResponse;
import com.github.redevizinha.models.bubble.dto.BubbleRequest;
import com.github.redevizinha.models.bubble.dto.BubbleResponse;
import com.github.redevizinha.models.bubble.service.BubbleMemberService;
import com.github.redevizinha.models.bubble.service.BubbleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bubbles")
public class BubbleController {

    private final BubbleService bubbleService;
    private final BubbleMemberService bubbleMemberService;

    @PostMapping
    public BubbleResponse createBubble(@Valid @RequestBody BubbleRequest request) {
        return bubbleService.createBubble(request);
    }

    @GetMapping
    public Page<BubbleResponse> getAllBubbles(Pageable pageable) {
        return bubbleService.getAllBubbles(pageable);
    }

    @GetMapping("/{id}")
    public BubbleResponse getBubbleById(@PathVariable Long id) {
        return bubbleService.getBubbleById(id);
    }

    @PutMapping("/{id}")
    public BubbleResponse updateBubble(
            @PathVariable Long id,
            @Valid @RequestBody BubbleRequest request
    ) {
        return bubbleService.updateBubble(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBubble(@PathVariable Long id) {
        bubbleService.deleteBubble(id);
    }

    @GetMapping("/{id}/users")
    public Page<BubbleMemberResponse> listUsersInBubble(@PathVariable Long id, Pageable pageable) {
        return bubbleMemberService.listUsersInBubble(id, pageable);
    }

    @PostMapping("/{id}/join")
    public BubbleMemberResponse joinBubble(@PathVariable Long id) {
        return bubbleMemberService.joinBubble(id);
    }

    @GetMapping("/{id}/friends")
    public Page<BubbleMemberResponse> getFriendsInBubble(@PathVariable Long id, Pageable pageable) {
        return bubbleMemberService.getFriendsInBubble(id, pageable);
    }

    @PostMapping("/{id}/notify")
    public String notifyFriends(@PathVariable Long id) {
        return bubbleMemberService.notifyFriends(id);
    }
}
