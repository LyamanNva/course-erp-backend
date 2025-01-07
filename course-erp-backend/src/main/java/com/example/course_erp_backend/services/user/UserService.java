package com.example.course_erp_backend.services.user;

import com.example.course_erp_backend.models.mybatis.user.User;

public interface UserService {
    void insert(User user);
    User getByEmail(String email);
}
