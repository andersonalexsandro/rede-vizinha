package com.github.redevizinha.models.group.repository;

import com.github.redevizinha.models.group.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByNameIgnoreCase(String name);
    List<Tag> findByNameInIgnoreCase(List<String> names); // opcional
}
