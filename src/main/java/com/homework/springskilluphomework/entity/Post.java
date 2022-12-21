package com.homework.springskilluphomework.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post extends TimeStamped{

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId; // 게시글의 id값

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(nullable = false)
    private String username; // 게시글 작성자 = 유저명 그대로 사용

    @Column(nullable = false)
    private String content; // 게시글 내용

    @Column
    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, orphanRemoval = true) // 야는 왜 final이고 싶어하는거지...?
    private List<Comment> commentList = new ArrayList<Comment>(); // 게시글에 달린 댓글의 리스트

    public Post(String title, String username, String content) {
        this.title = title;
        this.username = username;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 비밀번호 확인
    public boolean CheckUsernameIsAuthor(String username){
        return this.getUsername().equals(username);
    }


}
