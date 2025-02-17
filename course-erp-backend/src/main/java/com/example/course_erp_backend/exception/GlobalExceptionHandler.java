package com.example.course_erp_backend.exception;

import com.example.course_erp_backend.models.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<?>> handleBaseException(BaseException ex) {
        return ResponseEntity.status(ex.getResponseMessage().status()).body(BaseResponse.error(ex));
    }

//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<BaseResponse<?>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
//        BaseException baseException = (BaseException) ex.getCause();
//        return ResponseEntity.status(baseException.getResponseMessage().status()).body(BaseResponse.error(baseException));
//    }
}
