package com.homework.springskilluphomework.entity;

import com.homework.springskilluphomework.dto.CommentResponseDto;
import com.homework.springskilluphomework.dto.PostResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @Column // 뭔가... db에 칼럼 자체가 생성이 안됨... 이부분 이해를 못했나봄.....
    @OneToMany(mappedBy = "post", cascade=CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();// 게시글에 달린 댓글의 리스트

    public Post(String title, String username, String content) {
        this.title = title;
        this.username = username;
        this.content = content;
        this.commentList = new ArrayList<>();
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 작성자일치 여부 확인
    public boolean checkUsernameIsAuthor(String username){
        return this.getUsername().equals(username);
    }

    //게시글의 댓글목록에 댓글 추가 // 지금 여기가 문제인듯.
    public void addCommentInOwnCommentList(Comment comment){
        this.commentList.add(comment);
    }

    // post로 PostResponseDto 만드는 메소드
    public PostResponseDto makePostResponseDto(){
        LocalDateTime getDtoCreatedAt = this.getCreatedAt();
        LocalDateTime getDtoModifiedAt = this.getModifiedAt();
        Long getDtoPostId = this.getPostId();
        String getDtoTitle = this.getTitle();
        String getDtoUsername = this.getUsername();
        String getDtoContent = this.getContent();
        List<CommentResponseDto> getDtoCommentList = this.getCommentList().stream().map(Comment::makeCommentResponseDto).toList();
        return new PostResponseDto(getDtoCreatedAt, getDtoModifiedAt, getDtoPostId, getDtoTitle, getDtoUsername, getDtoContent, getDtoCommentList);

    }




}
