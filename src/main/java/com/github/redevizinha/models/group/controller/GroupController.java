package com.github.redevizinha.models.group.controller;

import com.github.redevizinha.models.group.dto.*;
import com.github.redevizinha.models.group.service.GroupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public GroupResponse create(@Valid @RequestBody GroupRequest request) {
        return groupService.create(request);
    }

    @GetMapping
    public Page<GroupResponse> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String tags,
            Pageable pageable
    ) {
        List<String> tagList = (tags == null || tags.isBlank())
                ? List.of()
                : Arrays.stream(tags.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        return groupService.search(q, tagList, pageable);
    }

    @GetMapping("/{id}")
    public GroupResponse getById(@PathVariable Long id) {
        return groupService.getById(id);
    }

    @PutMapping("/{id}")
    public GroupResponse update(
            @PathVariable Long id,
            @Valid @RequestBody GroupUpdateRequest request
    ) {
        return groupService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void closeIfInactive(@PathVariable Long id) {
        groupService.closeIfInactive(id);
    }

    @GetMapping("/{id}/chat")
    public Page<GroupMessageResponse> listMessages(
            @PathVariable Long id,
            Pageable pageable
    ) {
        return groupService.listMessages(id, pageable);
    }

    @PostMapping("/{id}/chat")
    public GroupMessageResponse postMessage(
            @PathVariable Long id,
            @Valid @RequestBody GroupMessageRequest request
    ) {
        return groupService.postMessage(id, request);
    }
}
