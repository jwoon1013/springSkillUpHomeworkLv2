package com.homework.springskilluphomework.entity;

import com.homework.springskilluphomework.dto.CommentResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId; // 댓글의 고유 댓글id값

    @ManyToOne(fetch = FetchType.LAZY) // 포스트랑 연결이 안돼.......
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post; // 댓글이 소속된 post

    @Column(nullable = false)
    private String username; // 댓글의 작성자명

    @Column(nullable = false)
    private String commentContent; // 댓글 내용

    public Comment(Post post, String commentAuthorName, String commentContent){
        this.post = post;
        this.username = commentAuthorName;
        this.commentContent = commentContent;
    }

    public void update(String content) {
        this.commentContent = content;
    }

    // 댓글 작성자 일치 여부 확인
    public boolean checkUsernameIsCommentAuthor(String username){return this.getUsername().equals(username);}

    //comment로 CommentResponseDto 만드는 메소드
    public CommentResponseDto makeCommentResponseDto(){
        LocalDateTime toDtoCreatedAt = this.getCreatedAt();
        LocalDateTime toDtoModifiedAt = this.getModifiedAt();
        Long toDtoPostId = this.getPost().getPostId();
        Long toDtoCommentId = this.getCommentId();
        String toDtoCommentAuthorName = this.getUsername();
        String toDtoCommentContent = this.getCommentContent();

        return new CommentResponseDto(toDtoCreatedAt,toDtoModifiedAt,toDtoPostId,toDtoCommentId,toDtoCommentAuthorName,toDtoCommentContent);
    }



}
