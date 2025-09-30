package com.github.redevizinha.models.user.repository;

import com.github.redevizinha.models.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
