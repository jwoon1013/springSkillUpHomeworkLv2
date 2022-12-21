package com.homework.springskilluphomework.entity;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 유저 고유 아이디값

    @Column(nullable = false, unique = true)
    private String username; // 유저명(최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9))

    @Column(nullable = false)
    private String password; // 유저비번(최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9))

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role; // 유저권한(현재 USER만 있음)


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRoleEnum.USER;
    }

    // 비밀번호 체크 기능
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
