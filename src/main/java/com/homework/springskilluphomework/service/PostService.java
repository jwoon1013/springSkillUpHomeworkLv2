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

    @Transactional // 게시글 작성 (lv2 수정)
    public PostResponseDto createPost(PostRequestDto postRequestDto, String username) {
            // 3. 토큰에서 가져온 사용자 정보로 DB에서 해당유저 찾아서 사용할 유저 객체 생성
            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
            );
            // 4. 토큰값이 유효한 회원만 게시글 작성 가능.
            Post post = postRequestDto.toEntity(user.getUsername());
            postRepository.save(post);
            return new PostResponseDto(post);
    } // create post 종료

    @Transactional(readOnly = true) //선택한 게시글 조회
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("id값이 일치하는 게시글이 없습니다.")
        );
        PostResponseDto postResponseDto = new PostResponseDto(post);

        return postResponseDto;
    } // getPost 종료


    @Transactional // 선택한 게시글 수정
    public PostResponseDto updatePost(Long postId, PostRequestDto postrequestDto, String username) {
        // 1. 해당 postId 포스트가 있나?
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("해당 postId 의 포스트가 존재하지 않습니다.")
        );
        // 2. 해당 유저가 작성한 포스트가 맞는지 검사 > 맞으면 수정 진행
        if (post.CheckUsernameIsAuthor(username)) {
            post.update(postrequestDto.getTitle(), postrequestDto.getContent());
        } else throw new RuntimeException("본인이 작성한 게시글만 수정할 수 있습니다.");

        // 3. PostResponseDto 생성후 리턴
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    } // updatePost 종료

    // 선택한 게시글 삭제
    public String deletePost(Long postId, String username) {
        // 1. 해당 postId 포스트가 있나?
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("해당 postId 의 포스트가 존재하지 않습니다.")
        );
            // 2. 유저 생성 (5 에서 유저네임 써야해서.. 이걸 안해도 될거같은데 방법을 모르겠음)
            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
            );

        // 3. 해당 유저가 작성한 포스트가 맞는지 검사 > 맞으면 삭제 진행
            if (post.CheckUsernameIsAuthor(user.getUsername())) {
                postRepository.deleteById(postId);
            } else throw new RuntimeException("본인이 작성한 게시글만 삭제할 수 있습니다.");

        return "게시글 삭제 성공!";
    }
}// postservice 클래스의 끝
