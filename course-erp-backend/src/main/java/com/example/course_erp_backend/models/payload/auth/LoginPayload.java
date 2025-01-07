package com.example.course_erp_backend.models.payload.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginPayload {
    String email;
    String password;
    boolean rememberMe;
}
