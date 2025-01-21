package com.example.course_erp_backend.repository;

import com.example.course_erp_backend.models.mybatis.course.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseRepository {
    void insert(Course course);
}
