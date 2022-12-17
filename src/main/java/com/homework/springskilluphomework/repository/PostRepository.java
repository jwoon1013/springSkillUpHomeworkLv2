package com.homework.springskilluphomework.repository;

import com.homework.springskilluphomework.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
