package com.homework.springskilluphomework.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long CommentId; // 댓글의 고유 댓글id값

    @Column
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post Post; // 댓글이 소속된 post

    @Column
    private String username; // 댓글의 작성자명

    @Column
    private String CommentContent; // 댓글 내용

}
