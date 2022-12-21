package com.homework.springskilluphomework.repository;

import com.homework.springskilluphomework.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByOrderByModifiedAtDesc(); // 전체 댓글 목록 리스트

    Optional<Comment> findByCommentId(Long commentId); // 특정 댓글 id 값으로 찾기

}
