package com.github.redevizinha.models.directMessage.controller;

import com.github.redevizinha.models.directMessage.dto.DirectMessageRequest;
import com.github.redevizinha.models.directMessage.dto.DirectMessageResponse;
import com.github.redevizinha.models.directMessage.service.DirectMessageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/messages")
public class DirectMessageController {

    private final DirectMessageService directMessageService;

    @PostMapping("/{id}")
    public DirectMessageResponse sendMessage(
            @PathVariable Long id,
            @Valid @RequestBody DirectMessageRequest request
    ) {
        return directMessageService.sendMessage(id, request);
    }

    @GetMapping("/{id}")
    public Page<DirectMessageResponse> getMessagesWithUser(
            @PathVariable Long id,
            Pageable pageable
    ) {
        return directMessageService.getMessagesWithUser(id, pageable);
    }
}
