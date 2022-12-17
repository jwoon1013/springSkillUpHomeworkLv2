package com.homework.springskilluphomework.dto;


import com.homework.springskilluphomework.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String content;

    public Post toEntity(String username){ // RequestDto의 내용을 토대로 post 생성

        return new Post(this.title, username, this.content);
    }

}


