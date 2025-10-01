package com.github.redevizinha.models.group.repository;

import com.github.redevizinha.models.group.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {


    @Query("""
        SELECT DISTINCT g FROM Group g
        LEFT JOIN g.groupTags gt
        LEFT JOIN gt.tag t
        WHERE g.closed = false
          AND (:q IS NULL OR g.name LIKE %:q%)
          AND (:tagsEmpty = true OR t.name IN (:tags))
        """)
    Page<Group> search(@Param("q") String q,
                       @Param("tagsEmpty") boolean tagsEmpty,
                       @Param("tags") List<String> tags,
                       Pageable pageable);
}
