package com.homework.springskilluphomework.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    @NotNull(message = "유저명은 비울 수 없습니다.")
    @Size(min= 4, max=10, message = "최소 4자 이상, 10자 이하만 가능합니다.")
    @Pattern(regexp="[a-z0-9]", message = "알파벳 소문자, 숫자만 가능합니다.")
    private String username; // 유저명(최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9))


    @NotNull(message = "비밀번호는 비울 수 없습니다.")
    @Size(min= 8, max=15, message = "최소 8자 이상, 15자 이하만 가능합니다.")
    @Pattern(regexp="[a-zA-Z0-9]", message = "알파벳 대/소문자, 숫자만 가능합니다.")
    private String password; // 유저비번(최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9))

    // signUpRequestDto와 LogInRequestDto 내용물은 같지만,
    // 기능차이가 있고 나중에 각자 내용 추가할수도 있으므로, 분리함.
}
