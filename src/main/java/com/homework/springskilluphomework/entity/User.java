package com.homework.springskilluphomework.entity;

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
    private String username; // 유저명

    @Column(nullable = false)
    private String password; // 유저비번

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role; // 유저권한


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
