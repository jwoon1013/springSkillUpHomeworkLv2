package com.homework.springskilluphomework.controller;

import com.homework.springskilluphomework.dto.PostRequestDto;
import com.homework.springskilluphomework.dto.PostResponseDto;

import com.homework.springskilluphomework.jwt.JwtUtil;
import com.homework.springskilluphomework.service.PostService;
import com.homework.springskilluphomework.service.TokenCheckService;
import com.homework.springskilluphomework.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final TokenCheckService tokenCheckService;
    private final JwtUtil jwtUtil;

    // 전체 게시글 목록 조회
    @GetMapping("/posts")
    public List<PostResponseDto> totalPostListResponseDto() {
        return postService.getPostList();
    }

    // 게시글 작성 (lv2 리펙토링)
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        String username = tokenCheckService.checkToken(token);
        return postService.createPost(postRequestDto, username);
    } // 게시글 작성 종료


    // 선택한 게시글 조회
    @GetMapping("/posts/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    // 선택한 게시글 수정 (lv2 리펙토링)
    @PutMapping("/posts/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postrequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        String username = tokenCheckService.checkToken(token);
        return postService.updatePost(postId, postrequestDto, username);
    }

    // 선택한 게시글 삭제 (lv2 리펙토링)
    @DeleteMapping("posts/{postId}")
    public String deletePost(@PathVariable Long postId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        String username = tokenCheckService.checkToken(token);
        return postService.deletePost(postId, username);
    }

}
