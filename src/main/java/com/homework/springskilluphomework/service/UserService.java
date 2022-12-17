package com.homework.springskilluphomework.service;

import com.homework.springskilluphomework.dto.LogInRequestDto;
import com.homework.springskilluphomework.dto.SignUpRequestDto;
import com.homework.springskilluphomework.entity.User;
import com.homework.springskilluphomework.jwt.JwtUtil;
import com.homework.springskilluphomework.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional // 회원가입
    public String signUp(SignUpRequestDto signUpRequestDto) {
        String username = signUpRequestDto.getUsername();
        String password = signUpRequestDto.getPassword();

        // 회원명 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if(found.isPresent()){
            throw new IllegalArgumentException("유저명이 중복되었습니다.");
        }
        // 유저 생성 후 DB에 저장
        User user = new User(username, password);
        userRepository.save(user);

        return "회원가입 성공";
    }

    @Transactional(readOnly = true) // 로그인
    public String logIn(LogInRequestDto logInRequestDto, HttpServletResponse response) {
        String username = logInRequestDto.getUsername();
        String password = logInRequestDto.getPassword();

        // 1. 사용자 있는지 확인
        User user = userRepository.findByUsername(username). orElseThrow(
                () -> new IllegalArgumentException("해당 이름으로 등록된 유저가 없습니다.")
        );

        // 2. 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 헤더에 올리기
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

        return "로그인 완료!";
    }
}
