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
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        post.addCommentInOwnCommentList(comment);
        return commentResponseDto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto commentRequestDto, String username) {
        //1. 해당 postId의 포스트가 있는지 확인
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("해당 postId 의 포스트가 존재하지 않습니다.")
        );
        //2. 해당 commentId의 코멘트가 있는지 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException("해당 commentId 의 댓글이 존재하지 않습니다.")
        );

        //3. 컨트롤러에서 받아온 username 으로 user 생성
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        //4. user 의 권한 확인 (ADMIN / USER)
        switch (user.getUserRole()) {
            case ADMIN:
                    // 작성자도 바꿔야 하나 고민했지만, 보통 관리자가 게시글이나 댓글 수정하면 작성자명은 그대로고 내용만 "관리자에 의해 삭제된 게시글입니다" 식으로 떠서 내용만 바꾸게 둠.
                    comment.update(commentRequestDto.getComment());
                break;
            case USER:
                // 4. 게시글 작성자와 유저네임 일치하나 검사 > 맞으면 삭제 진행
                if (comment.checkUsernameIsCommentAuthor(username)) {
                    comment.update(commentRequestDto.getComment());
                } else throw new RuntimeException("본인이 작성한 게시글만 수정할 수 있습니다.");
                break;
        }
        //4. CommentResponseDto 리턴
        return new CommentResponseDto(comment);
    }

    @Transactional
    public String deleteComment(Long postId, Long commentId, String username) {
        //1. 해당 postId의 포스트가 있는지 확인
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("해당 postId 의 포스트가 존재하지 않습니다.")
        );
        //2. 해당 commentId의 코멘트가 있는지 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException("해당 commentId 의 댓글이 존재하지 않습니다.")
        );

        //3. 컨트롤러에서 받아온 username 으로 user 생성
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        //4. 권한 확인 > 관리자일시 바로삭제 가능
        switch(user.getUserRole()){
            case ADMIN :
                commentRepository.deleteById(commentId);
                break;
            case USER:
                // 댓글 작성자와 유저네임 일치할시 삭제
                if(comment.checkUsernameIsCommentAuthor(user.getUsername())){
                    commentRepository.deleteById(commentId);
                } else throw new RuntimeException("본인이 작성한 댓글만 삭제할 수 있습니다.");
                break;
        }
        return "댓글 삭제 성공!";
    }
}
