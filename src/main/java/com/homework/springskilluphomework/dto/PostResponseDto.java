package com.homework.springskilluphomework.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private LocalDateTime createdAt; //작성시간

    private LocalDateTime modifiedAt; //수정시간

    private Long postId; //게시글 id


    private String title; //글제목


    private String username; //글작성자


    private String content; //글내용

    private List<CommentResponseDto> commentResponseDtoList; // 댓글리스트

}


