package com.example.course_erp_backend.models.mappers;

import com.example.course_erp_backend.models.enums.course.CourseStatus;
import com.example.course_erp_backend.models.mybatis.course.Course;
import com.example.course_erp_backend.models.payload.auth.SignUpPayload;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-19T19:50:29+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
public class CourseEntityMapperImpl implements CourseEntityMapper {

    @Override
    public Course fromSignUpPayload(SignUpPayload payload) {
        if ( payload == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.name( payload.getCourseName() );

        course.status( CourseStatus.ACTIVE );

        return course.build();
    }
}
