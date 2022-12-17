package com.homework.springskilluphomework.controller;

import com.homework.springskilluphomework.dto.PostRequestDto;
import com.homework.springskilluphomework.dto.PostResponseDto;
import com.homework.springskilluphomework.service.PostService;
import com.homework.springskilluphomework.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    // 전체 게시글 목록 조회
    @GetMapping("/posts")
    public List<PostResponseDto> totalPostListResponseDto(){
        return postService.getPostList();
    }

    // 게시글 작성
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.createPost(postRequestDto, request);
    }

    // 선택한 게시글 조회
    @GetMapping("/posts/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) {return postService.getPost(postId);
    }

    // 선택한 게시글 수정
    @PutMapping("/posts/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postrequestDto, HttpServletRequest request){
        return postService.updatePost(postId, postrequestDto, request);
        // 여기서 뭐가 안되가지고(계속 static이어야 한다고 요구함) 한참 해맸는데, PostService 로 써놔서 그런거였음 -_-;
    }

    @DeleteMapping("posts/{postId}")
    public String deletePost(@PathVariable Long postId, HttpServletRequest request){
        return postService.deletePost(postId, request);
    }

}
