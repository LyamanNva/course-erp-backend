package com.example.course_erp_backend.repository;

import com.example.course_erp_backend.models.mybatis.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserRepository {

    void insert(User user);

    Optional<User> findByEmail(@Param("email")String email);
}
