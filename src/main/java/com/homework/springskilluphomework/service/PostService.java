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

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Transactional // 게시글 작성
    public PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        //1. Request 에서 토큰 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;// << (궁금) 이건 정확히 무슨용도 + 이름이 왜 이거지?

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
}// postservice 클래스의 끝
