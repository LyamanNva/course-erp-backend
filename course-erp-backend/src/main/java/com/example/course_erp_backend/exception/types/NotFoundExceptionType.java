package com.example.course_erp_backend.exception.types;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotFoundExceptionType {
    String target;
    Map<String, Object> fields;

    public static NotFoundExceptionType of(String target, Map<String, Object> fields) {
        return NotFoundExceptionType.builder()
                .fields(fields)
                .target(target)
                .build();
    }
}
