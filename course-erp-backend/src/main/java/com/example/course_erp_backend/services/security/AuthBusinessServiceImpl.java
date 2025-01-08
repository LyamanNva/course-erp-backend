package com.example.course_erp_backend.services.security;

import com.example.course_erp_backend.models.base.BaseResponse;
import com.example.course_erp_backend.models.dto.RefreshTokenDto;
import com.example.course_erp_backend.models.mybatis.user.User;
import com.example.course_erp_backend.models.payload.auth.LoginPayload;
import com.example.course_erp_backend.models.payload.auth.RefreshTokenPayload;
import com.example.course_erp_backend.models.response.auth.LoginResponse;
import com.example.course_erp_backend.services.user.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthBusinessServiceImpl implements AuthBusinessService {

    private final AuthenticationManager authenticationManager;
    private final AccessTokenManager accessTokenManager;
    private final RefreshTokenManager refreshTokenManager;
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @Override
    public LoginResponse login(LoginPayload payload) {
        authenticate(payload);

        return prepareLoginResponse(payload.getEmail(), payload.isRememberMe());

    }

    @Override
    public LoginResponse refresh(RefreshTokenPayload payload) {
        return prepareLoginResponse(
                refreshTokenManager.getEmail(payload.getRefreshToken()),
                payload.isRememberMe());

    }

    @Override
    public void logout() {
        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("{} user logout succeed",userDetails.getUsername());
    }

    @Override
    public void setAuthentication(String email) {
        UserDetails userDetails=userDetailsService.loadUserByUsername(email);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities())
        );
    }
    //private util methods

    private void authenticate(LoginPayload request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Exception");
        }
    }

    private LoginResponse prepareLoginResponse(String email,boolean rememberMe) {
        User user=userService.getByEmail(email);
        return LoginResponse.builder()
                .accessToken(accessTokenManager.generate(user))
                .refreshToken(refreshTokenManager.generate(
                        RefreshTokenDto.builder().user(user).rememberMe(rememberMe)
                                .build()
                ))
                .build();
    }
}
