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
    private String author;

    @Column(nullable = false)
    private String content;

    public Post(String title, String author, String content, String password) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

    public void update(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }


}
