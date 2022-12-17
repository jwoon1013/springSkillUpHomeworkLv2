package com.homework.springskilluphomework.dto;

import com.homework.springskilluphomework.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter

@Setter
@NoArgsConstructor
public class PostResponseDto {
    private LocalDateTime createdAt; //작성시간

    private LocalDateTime modifiedAt; //수정시간

    private Long postId; //게시글 id


    private String title; //글제목


    private String username; //글작성자


    private String content; //글내용

    public PostResponseDto(Post post){
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.username = post.getUsername();
        this.content = post.getContent();
    }

}


