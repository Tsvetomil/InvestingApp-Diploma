package com.isb.repository;

import com.isb.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByProjectIDOrderByTimestampDesc(long id);
    Comment getById(long id);
}
