package com.github.redevizinha.models.user.service;

import com.github.redevizinha.models.user.dto.UserCreateRequest;
import com.github.redevizinha.models.user.dto.UserPrivacyUpdate;
import com.github.redevizinha.models.user.dto.UserPutRequest;
import com.github.redevizinha.models.user.dto.UserResponse;
import com.github.redevizinha.models.user.entity.User;
import com.github.redevizinha.models.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        User user = mapper.map(userCreateRequest, User.class);
        user = userRepository.save(user);
        return mapper.map(user, UserResponse.class);
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> mapper.map(user, UserResponse.class));
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return mapper.map(user, UserResponse.class);
    }

    public UserResponse putUser(Long id, UserPutRequest userPutRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        mapper.map(userPutRequest, user);

        User updated = userRepository.save(user);
        return mapper.map(updated, UserResponse.class);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    public UserResponse updatePrivacy(Long id, UserPrivacyUpdate userPrivacyUpdate) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setPublicProfile(userPrivacyUpdate.isPublicProfile());

        User updated = userRepository.save(user);
        return mapper.map(updated, UserResponse.class);
    }
}
