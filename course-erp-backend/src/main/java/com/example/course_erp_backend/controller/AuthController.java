package com.example.course_erp_backend.controller;

import com.example.course_erp_backend.models.base.BaseResponse;
import com.example.course_erp_backend.models.dto.RefreshTokenDto;
import com.example.course_erp_backend.models.mybatis.user.User;
import com.example.course_erp_backend.models.payload.auth.LoginPayload;
import com.example.course_erp_backend.models.response.auth.LoginResponse;
import com.example.course_erp_backend.services.security.AccessTokenManager;
import com.example.course_erp_backend.services.security.RefreshTokenManager;
import com.example.course_erp_backend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AccessTokenManager accessTokenManager;
    private final RefreshTokenManager refreshTokenManager;

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginPayload payload) {

        authenticate(payload);

        User user=userService.getByEmail(payload.getEmail());

        return BaseResponse.success(LoginResponse.builder()
                .accessToken(accessTokenManager.generate(user))
                .refreshToken(refreshTokenManager.generate(
                        RefreshTokenDto.builder().user(user).rememberMe(payload.isRememberMe()).build()
                ))
                .build()
        );
    }

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    private void authenticate(LoginPayload request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        }catch (Exception e) {
            throw new RuntimeException("Exception");
        }
    }
}
