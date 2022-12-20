package com.homework.springskilluphomework.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {

    @NotBlank
    @Size(min= 4, max=10)
    @Pattern(regexp="^[a-z0-9]*$")
    private String username; // 유저명(최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9))


    @NotBlank
    @Size(min= 8, max=15)
    @Pattern(regexp="^[a-zA-Z0-9!@#$%^&*()]*$")
    private String password; // 유저비번(최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9))
}
