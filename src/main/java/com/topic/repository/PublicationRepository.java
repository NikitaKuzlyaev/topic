package com.topic.repository;

import com.topic.entity.main.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    @Query("SELECT p FROM Publication p WHERE p.board.id = :boardId")
    List<Publication> findAllByBoardId(@Param("boardId") Long boardId);

    // no N+1 problem
    @Query("SELECT p FROM Publication p " +
            "LEFT JOIN FETCH p.author " +
            "WHERE p.board.id = :boardId " +
            "ORDER BY p.createdAt DESC")
    List<Publication> findAllByBoardIdWithAuthor(@Param("boardId") Long boardId);

}
