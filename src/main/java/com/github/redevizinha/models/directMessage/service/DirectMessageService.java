package com.github.redevizinha.models.directMessage.service;

import com.github.redevizinha.models.directMessage.dto.DirectMessageRequest;
import com.github.redevizinha.models.directMessage.dto.DirectMessageResponse;
import com.github.redevizinha.models.directMessage.entity.DirectMessage;
import com.github.redevizinha.models.directMessage.repository.DirectMessageRepository;
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
public class DirectMessageService {

    private final UserRepository userRepository;
    private final UserContextProvider userContextProvider;
    private final DirectMessageRepository directMessageRepository;
    private final ModelMapper modelMapper;

    public DirectMessageResponse sendMessage(Long receiverId, DirectMessageRequest request) {
        Long senderId = userContextProvider.getCurrentUserId();

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        DirectMessage message = new DirectMessage();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(request.getContent());

        DirectMessage saved = directMessageRepository.save(message);

        return modelMapper.map(saved, DirectMessageResponse.class);
    }

    public Page<DirectMessageResponse> getMessagesWithUser(Long otherUserId, Pageable pageable) {
        Long currentUserId = userContextProvider.getCurrentUserId();

        return directMessageRepository.findConversation(currentUserId, otherUserId, pageable)
                .map(msg -> modelMapper.map(msg, DirectMessageResponse.class));
    }
}
