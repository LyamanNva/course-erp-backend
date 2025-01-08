package com.example.course_erp_backend.services.security;

import com.example.course_erp_backend.models.payload.auth.LoginPayload;
import com.example.course_erp_backend.models.payload.auth.RefreshTokenPayload;
import com.example.course_erp_backend.models.response.auth.LoginResponse;

public interface AuthBusinessService {
    LoginResponse login(LoginPayload payload);
    LoginResponse refresh(RefreshTokenPayload payload);
    void logout();
    void setAuthentication(String email);
}
