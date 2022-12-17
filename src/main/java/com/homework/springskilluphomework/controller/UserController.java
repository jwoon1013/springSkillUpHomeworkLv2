package com.homework.springskilluphomework.controller;

import com.homework.springskilluphomework.dto.LogInRequestDto;
import com.homework.springskilluphomework.dto.SignUpRequestDto;

import com.homework.springskilluphomework.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/signup")
    public String signUp(SignUpRequestDto signUpRequestDto){

        return userService.signUp(signUpRequestDto);
    }

    @ResponseBody
    @PostMapping("/auth/login")
    public String logIn(@RequestBody LogInRequestDto logInRequestDto, HttpServletResponse response){
        return userService.logIn(logInRequestDto, response);

    }
}
