package com.example.course_erp_backend.services.base;

public interface TokenGenerator <T>{
    String generate(T obj);
}
