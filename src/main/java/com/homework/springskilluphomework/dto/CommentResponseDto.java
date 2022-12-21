package com.homework.springskilluphomework.dto;

import com.homework.springskilluphomework.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private LocalDateTime createdAt; //작성시간

    private LocalDateTime modifiedAt; //수정시간

    private Long postId; //댓글이 소속된 게시글의 id

    private Long commentId; // 생성한 댓글의 고유 아이디

    private String commentAuthorName; // 댓글 작성자명

    private String content; // 댓글 내용

    public CommentResponseDto(Comment comment){
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.postId =comment.getPost().getPostId();
        this.commentId=comment.getCommentId();
        this.commentAuthorName=comment.getUsername();
        this.content= comment.getCommentContent();
    }

}
