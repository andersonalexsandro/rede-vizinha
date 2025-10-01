package com.github.redevizinha.models.group.repository;

import com.github.redevizinha.models.group.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("""
           SELECT DISTINCT g FROM Group g
           LEFT JOIN g.groupTags gt
           LEFT JOIN gt.tag t
           WHERE g.closed = false
             AND (:q IS NULL OR LOWER(g.name) LIKE LOWER(CONCAT('%', :q, '%')))
             AND (:tagsEmpty = TRUE OR LOWER(t.name) IN :tagsLower)
           """)
    Page<Group> search(String q, boolean tagsEmpty, List<String> tagsLower, Pageable pageable);
}
