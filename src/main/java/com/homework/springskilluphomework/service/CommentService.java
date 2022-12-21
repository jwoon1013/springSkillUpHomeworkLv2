package com.homework.springskilluphomework.service;

import com.homework.springskilluphomework.dto.CommentRequestDto;
import com.homework.springskilluphomework.dto.CommentResponseDto;
import com.homework.springskilluphomework.dto.PostResponseDto;
import com.homework.springskilluphomework.entity.Comment;
import com.homework.springskilluphomework.entity.Post;
import com.homework.springskilluphomework.entity.User;
import com.homework.springskilluphomework.jwt.JwtUtil;
import com.homework.springskilluphomework.repository.CommentRepository;
import com.homework.springskilluphomework.repository.PostRepository;
import com.homework.springskilluphomework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, String username) {
        //1. 해당 postId의 포스트가 있는지 확인
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("해당 postId 의 포스트가 존재하지 않습니다.")
        );
        //2. 컨트롤러에서 받아온 username으로 user 생성
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        //3. Comment 생성 후, comment레파지토리에 저장 /해당 포스트의 commentlist에 저장  /CommentResponseDto 생성 후 리턴
        Comment comment = commentRequestDto.toEntity(post, username);
        commentRepository.save(comment);
        post.getCommentList().add(comment);
        return new CommentResponseDto(comment);
    }
}
