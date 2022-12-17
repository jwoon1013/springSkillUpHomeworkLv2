package com.homework.springskilluphomework.service;

import com.homework.springskilluphomework.dto.PostRequestDto;
import com.homework.springskilluphomework.dto.PostResponseDto;
import com.homework.springskilluphomework.entity.Post;
import com.homework.springskilluphomework.entity.User;
import com.homework.springskilluphomework.jwt.JwtUtil;
import com.homework.springskilluphomework.repository.PostRepository;
import com.homework.springskilluphomework.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Transactional(readOnly = true) // 전체 게시글 목록 조회 (이건 토큰인증 안해도 누구나 볼수잇어)
    public List<PostResponseDto> getPostList() {
        List<Post> list = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> totalPostList = list.stream().map(post -> new PostResponseDto(post)).collect(Collectors.toList());
        return totalPostList;
    } // getPostList 종료

    @Transactional // 게시글 작성
    public PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        //1. Request 에서 토큰 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;// << (궁금) 이건 토큰통해서 가져온 유저정보 담는 그릇인거같은데 + 이름이 왜 이거지?

        //2. 토큰이 있는 경우에만 개시글 작성 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰이 있는경우 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰값이 잘못됨");
            }

            // 3. 토큰에서 가져온 사용자 정보로 DB에서 해당유저 찾아서 사용할 유저 객체 생성
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
            );

            // 4. 토큰값이 유효한 회원만 게시글 작성 가능.
            Post post = postRequestDto.toEntity(user.getUsername());
            postRepository.save(post);
            return new PostResponseDto(post);

        } else {
            return null;
        }
    } // create post 종료

    @Transactional(readOnly = true) //선택한 게시글 조회
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("id값이 일치하는 게시글이 없습니다.")
        );
        PostResponseDto postResponseDto = new PostResponseDto(post);

        return postResponseDto;
    } // getPost 종료


    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto postrequestDto, HttpServletRequest request) {
        // 1. 해당 postId 포스트가 있나?
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("해당 postId 의 포스트가 존재하지 않습니다.")
        );

        //2. Request 에서 토큰 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 3. 토큰이 유효한지 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰이 있는경우 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰값이 잘못됨");
            }

            // 4. 유저 생성 (5에서 username 필요해서 생성하는데, 이걸 안해도 쓸수있을거같긴한데 방법을 잘 모르겠음)
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
            );

            // 5. 해당 유저가 작성한 포스트가 맞는지 검사 > 맞으면 수정 진행
            if (post.getUsername().equals(user.getUsername())) {
                post.update(postrequestDto.getTitle(), postrequestDto.getContent());
            } else throw new RuntimeException("본인이 작성한 게시글만 수정할 수 있습니다.");
        }else {
            return null;
        }
        // 6. PostResponseDto 생성후 리턴
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    } // updatePost 종료

    public String deletePost(Long postId, HttpServletRequest request) {
        // 1. 해당 postId 해당하는 포스트 있나?
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("해당 postId 의 포스트가 존재하지 않습니다.")
        );

        // 2. Request에서 토큰 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 3. 토큰이 유효한지 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰이 있는경우 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰값이 잘못됨");
            }
            // 4. 유저 생성 (5 에서 유저네임 써야해서.. 이걸 안해도 될거같은데 방법을 모르겠음)
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
            );

        // 5. 해당 유저가 작성한 포스트가 맞는지 검사 > 맞으면 삭제 진행
            if (post.getUsername().equals(user.getUsername())) {
                postRepository.deleteById(postId);
            } else throw new RuntimeException("본인이 작성한 게시글만 삭제할 수 있습니다.");
        }else {
            return null;
        }

        return "게시글 삭제 성공!";
    }
}// postservice 클래스의 끝
