package com.homework.springskilluphomework.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long CommentId; // 댓글의 고유 댓글id값

    private Post Post; // 댓글이 소속된 post

    private String username; // 댓글의 작성자명

    private String CommentContent; // 댓글 내용

}
