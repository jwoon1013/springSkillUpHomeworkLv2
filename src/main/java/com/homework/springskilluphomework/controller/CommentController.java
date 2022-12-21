package com.homework.springskilluphomework.controller;

import com.homework.springskilluphomework.dto.CommentRequestDto;
import com.homework.springskilluphomework.dto.CommentResponseDto;
import com.homework.springskilluphomework.jwt.JwtUtil;
import com.homework.springskilluphomework.service.CommentService;
import com.homework.springskilluphomework.service.TokenCheckService;
import com.homework.springskilluphomework.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final TokenCheckService tokenCheckService;
    private final JwtUtil jwtUtil;

    // 댓글 작성
    public CommentResponseDto createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        String username = tokenCheckService.checkToken(token);
        return commentService.createComment(postId, commentRequestDto, username);
    }

    // 댓글 수정
    // 댓글 삭제
}
