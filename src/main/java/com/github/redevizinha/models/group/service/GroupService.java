package com.github.redevizinha.models.group.service;

import com.github.redevizinha.commons.Role;
import com.github.redevizinha.models.bubble.entity.Bubble;
import com.github.redevizinha.models.bubble.repository.BubbleRepository;
import com.github.redevizinha.models.group.dto.*;
import com.github.redevizinha.models.group.entity.*;
import com.github.redevizinha.models.group.repository.*;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupMessageRepository groupMessageRepository;
    private final TagRepository tagRepository;
    private final GroupTagRepository groupTagRepository;
    private final BubbleRepository bubbleRepository;
    private final UserRepository userRepository;
    private final UserContextProvider userContext;
    private final ModelMapper mapper;

    private void ensureCreator(Group g, Long currentUserId) {
        if (!g.getCreator().getId().equals(currentUserId)) {
            throw new SecurityException("Only the creator can perform this action");
        }
    }

    private void replaceTags(Group group, List<String> tags) {
        groupTagRepository.deleteByGroup(group);

        if (tags == null || tags.isEmpty()) return;

        List<String> lower = tags.stream()
                .filter(Objects::nonNull)
                .map(s -> s.trim().toLowerCase())
                .filter(s -> !s.isEmpty())
                .distinct()
                .toList();

        for (String name : lower) {
            Tag tag = tagRepository.findByNameIgnoreCase(name)
                    .orElseGet(() -> {
                        Tag t = new Tag();
                        t.setName(name);
                        return tagRepository.save(t);
                    });
            GroupTag gt = new GroupTag();
            gt.setGroup(group);
            gt.setTag(tag);
            groupTagRepository.save(gt);
        }
    }

    private List<String> tagsOf(Group group) {
        return groupTagRepository.findByGroup(group).stream()
                .map(gt -> gt.getTag().getName())
                .toList();
    }

    public GroupResponse create(GroupRequest req) {
        Long uid = userContext.getCurrentUserId();

        Bubble bubble = bubbleRepository.findById(req.getBubbleId())
                .orElseThrow(() -> new EntityNotFoundException("Bubble not found"));
        User creator = userRepository.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Group g = new Group();
        g.setBubble(bubble);
        g.setCreator(creator);
        g.setName(req.getName());
        g.setDescription(req.getDescription());
        g.setClosed(false);
        g.setLastActivity(LocalDateTime.now());

        Group saved = groupRepository.save(g);

        replaceTags(saved, req.getTags());

        if (!groupMemberRepository.existsByGroupIdAndUserId(saved.getId(), uid)) {
            GroupMember gm = new GroupMember();
            gm.setGroup(saved);
            gm.setUser(creator);
            gm.setRole(Role.ADMIN);
            groupMemberRepository.save(gm);
        }

        GroupResponse resp = mapper.map(saved, GroupResponse.class);
        resp.setBubbleId(saved.getBubble().getId());
        resp.setCreatorId(saved.getCreator().getId());
        resp.setTags(tagsOf(saved));
        return resp;
    }

    public Page<GroupResponse> search(String q, List<String> tags, Pageable pageable) {
        List<String> tagsLower = (tags == null ? List.<String>of() :
                tags.stream().map(s -> s.toLowerCase()).toList());
        boolean tagsEmpty = tagsLower.isEmpty();

        return groupRepository.search(
                (q == null || q.isBlank()) ? null : q.trim(),
                tagsEmpty, tagsLower, pageable
        ).map(g -> {
            GroupResponse r = mapper.map(g, GroupResponse.class);
            r.setBubbleId(g.getBubble().getId());
            r.setCreatorId(g.getCreator().getId());
            r.setTags(tagsOf(g));
            return r;
        });
    }

    public GroupResponse getById(Long id) {
        Group g = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        GroupResponse r = mapper.map(g, GroupResponse.class);
        r.setBubbleId(g.getBubble().getId());
        r.setCreatorId(g.getCreator().getId());
        r.setTags(tagsOf(g));
        return r;
    }

    public GroupResponse update(Long id, GroupUpdateRequest req) {
        Long uid = userContext.getCurrentUserId();
        Group g = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        ensureCreator(g, uid);

        if (req.getDescription() != null) g.setDescription(req.getDescription());
        Group saved = groupRepository.save(g);

        replaceTags(saved, req.getTags());

        GroupResponse r = mapper.map(saved, GroupResponse.class);
        r.setBubbleId(saved.getBubble().getId());
        r.setCreatorId(saved.getCreator().getId());
        r.setTags(tagsOf(saved));
        return r;
    }

    // fecha grupo caso lastActivity > 24h
    public void closeIfInactive(Long id) {
        Group g = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        LocalDateTime limit = LocalDateTime.now().minusHours(24);
        if (g.getLastActivity().isAfter(limit)) {
            throw new IllegalStateException("Group still active in the last 24h");
        }
        g.setClosed(true);
        groupRepository.save(g);
    }

    public Page<GroupMessageResponse> listMessages(Long groupId, Pageable pageable) {
        return groupMessageRepository.findByGroupIdOrderByCreatedAtAsc(groupId, pageable)
                .map(m -> {
                    GroupMessageResponse r = mapper.map(m, GroupMessageResponse.class);
                    r.setGroupId(m.getGroup().getId());
                    r.setAuthorId(m.getAuthor().getId());
                    r.setSentAt(m.getCreatedAt());
                    return r;
                });
    }

    public GroupMessageResponse postMessage(Long groupId, GroupMessageRequest req) {
        Long uid = userContext.getCurrentUserId();
        Group g = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        if (g.isClosed()) throw new IllegalStateException("Group is closed");

        User author = userRepository.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!groupMemberRepository.existsByGroupIdAndUserId(groupId, uid)) {
            GroupMember gm = new GroupMember();
            gm.setGroup(g);
            gm.setUser(author);
            gm.setRole(Role.MEMBER);
            groupMemberRepository.save(gm);
        }

        GroupMessage msg = new GroupMessage();
        msg.setGroup(g);
        msg.setAuthor(author);
        msg.setContent(req.getContent());

        GroupMessage saved = groupMessageRepository.save(msg);

        g.setLastActivity(LocalDateTime.now());
        groupRepository.save(g);

        GroupMessageResponse r = mapper.map(saved, GroupMessageResponse.class);
        r.setGroupId(g.getId());
        r.setAuthorId(author.getId());
        r.setSentAt(saved.getCreatedAt());
        return r;
    }

    public GroupJoinResponse joinGroup(Long groupId) {
        Long uid = userContext.getCurrentUserId();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        if (group.isClosed()) {
            throw new IllegalStateException("Group is closed");
        }

        User user = userRepository.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (groupMemberRepository.existsByGroupIdAndUserId(groupId, uid)) {
            GroupJoinResponse resp = new GroupJoinResponse();
            resp.setGroupId(group.getId());
            resp.setUserId(uid);
            resp.setRole(Role.MEMBER);
            return resp;
        }

        GroupMember gm = new GroupMember();
        gm.setGroup(group);
        gm.setUser(user);
        gm.setRole(Role.MEMBER);
        groupMemberRepository.save(gm);

        GroupJoinResponse resp = new GroupJoinResponse();
        resp.setGroupId(group.getId());
        resp.setUserId(uid);
        resp.setRole(Role.MEMBER);
        return resp;
    }

    public Page<GroupMemberResponse> listMembers(Long groupId, Pageable pageable) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        return groupMemberRepository.findByGroupId(group.getId(), pageable)
                .map(member -> {
                    GroupMemberResponse resp = new GroupMemberResponse();
                    resp.setUserId(member.getUser().getId());
                    resp.setUserName(member.getUser().getName());
                    resp.setRole(member.getRole());
                    resp.setJoinedAt(member.getJoinedAt() != null ? member.getJoinedAt().toString() : null);
                    return resp;
                });
    }

    public Page<GroupResponse> listGroupsInBubble(Long bubbleId, Pageable pageable) {
        return groupRepository.findByBubbleId(bubbleId, pageable)
                .map(g -> {
                    GroupResponse r = mapper.map(g, GroupResponse.class);
                    r.setBubbleId(g.getBubble().getId());
                    r.setCreatorId(g.getCreator().getId());
                    r.setTags(tagsOf(g));
                    return r;
                });
    }

}
