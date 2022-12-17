package com.homework.springskilluphomework.repository;

import com.homework.springskilluphomework.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByModifiedAtDesc(); // 전체 게시글 목록 리스트

    Optional<Post> findById(Long postId); // 특정 게시글 id값으로 찾기

}
