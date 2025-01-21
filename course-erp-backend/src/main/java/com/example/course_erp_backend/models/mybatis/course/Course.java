package com.example.course_erp_backend.models.mybatis.course;

import com.example.course_erp_backend.models.enums.course.CourseStatus;
import com.example.course_erp_backend.models.mybatis.base.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor()
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course extends BaseEntity {
    String name;
    CourseStatus status;
}
