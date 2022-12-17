package com.homework.springskilluphomework.repository;


import com.homework.springskilluphomework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<User,Long> {
}
