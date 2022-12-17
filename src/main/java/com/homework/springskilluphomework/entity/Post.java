package com.homework.springskilluphomework.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Post extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 게시글의 id값

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(nullable = false)
    private String username; // 게시글 작성자 = 유저명 그대로 사용

    @Column(nullable = false)
    private String content; // 게시글 내용

    public Post(String title, String username, String content) {
        this.title = title;
        this.username = username;
        this.content = content;
    }

    public void update(String title, String username, String content) {
        this.title = title;
        this.username = username;
        this.content = content;
    }


}
