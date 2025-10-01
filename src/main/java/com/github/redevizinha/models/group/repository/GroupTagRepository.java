package com.github.redevizinha.models.group.repository;

import com.github.redevizinha.models.group.entity.Group;
import com.github.redevizinha.models.group.entity.GroupTag;
import com.github.redevizinha.models.group.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupTagRepository extends JpaRepository<GroupTag, Long> {
    List<GroupTag> findByGroup(Group group);
    void deleteByGroup(Group group);
}
