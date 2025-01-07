package com.example.course_erp_backend.services.base;

public interface TokenReader <T>{
    T read(String token);
}