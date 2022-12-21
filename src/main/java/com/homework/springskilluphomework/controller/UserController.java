package com.homework.springskilluphomework.controller;

import com.homework.springskilluphomework.dto.LogInRequestDto;
import com.homework.springskilluphomework.dto.SignUpRequestDto;

import com.homework.springskilluphomework.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    @ResponseBody
    @PostMapping("/signup")
    public String signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto){
        return userService.signUp(signUpRequestDto);

    }

    @ResponseBody
    @PostMapping("/login")
    public String logIn(@RequestBody LogInRequestDto logInRequestDto, HttpServletResponse response){
        return userService.logIn(logInRequestDto, response);


    }
}
