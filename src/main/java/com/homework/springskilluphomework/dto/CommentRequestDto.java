package com.homework.springskilluphomework.dto;

import com.homework.springskilluphomework.entity.Comment;
import com.homework.springskilluphomework.entity.Post;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String comment;

    public Comment toEntity(Post post, String commentAuthorName){
        return new Comment(post, commentAuthorName, this.comment);
    }

}
