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

    public CommentResponseDto(LocalDateTime createdAt, LocalDateTime modifiedAt, Long postId, Long commentId, String commentAuthorName, String CommentContent){
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.postId =postId;
        this.commentId=commentId;
        this.commentAuthorName=commentAuthorName;
        this.content=CommentContent;
    }

}
