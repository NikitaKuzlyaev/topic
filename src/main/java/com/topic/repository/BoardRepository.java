package com.topic.repository;

import com.topic.entity.main.Board;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b WHERE b.parent.id = :parentId")
    Page<Board> findAllWithParentId(Pageable pageable, @Param("parentId") Long parentId);

    @Query("SELECT b FROM Board b WHERE b.parent IS NULL")
    Page<Board> findAllWithoutParent(Pageable pageable);
}