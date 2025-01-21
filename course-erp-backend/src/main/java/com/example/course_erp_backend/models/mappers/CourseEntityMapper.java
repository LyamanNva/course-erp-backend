package com.example.course_erp_backend.models.mappers;

import com.example.course_erp_backend.models.mybatis.course.Course;
import com.example.course_erp_backend.models.payload.auth.SignUpPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseEntityMapper {
    CourseEntityMapper INSTANCE = Mappers.getMapper(CourseEntityMapper.class);
    @Mapping(target = "name",source="courseName")
    @Mapping(target = "status",constant = "ACTIVE")
    Course fromSignUpPayload(SignUpPayload payload);
}
