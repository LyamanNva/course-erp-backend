package com.example.course_erp_backend.controller;

import com.example.course_erp_backend.exception.BaseException;
import com.example.course_erp_backend.models.base.BaseResponse;
import com.example.course_erp_backend.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public BaseResponse<String> test() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return BaseResponse.success(userDetails.getUsername());
    }

    @GetMapping("/test/no-auth")
    public BaseResponse<String> testNoAuth() {
        userService.getByEmail("no-auth@example.com");
//throw BaseException.unexpected();
        return BaseResponse.success("Course ERP - No Auth");
    }
}
