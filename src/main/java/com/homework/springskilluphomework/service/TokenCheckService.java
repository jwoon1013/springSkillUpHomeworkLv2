package com.homework.springskilluphomework.service;

import com.homework.springskilluphomework.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenCheckService {
    private final JwtUtil jwtUtil;

    public String checkToken(String token){
        // 1. Claims 를 생성
        Claims claims;
        // 2. 토큰이 있는지, 유효한지 체크
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰값이 유효하지 않음");
            }
            return claims.getSubject(); // claims 의 subject (username)을 반환 (*claims 객체를 안넘기기 위함)
        } else throw new IllegalArgumentException("토큰값이 null 입니다.");
    }
}
