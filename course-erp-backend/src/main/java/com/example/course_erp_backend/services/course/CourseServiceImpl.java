package com.example.course_erp_backend.services.course;

import com.example.course_erp_backend.models.mybatis.course.Course;
import com.example.course_erp_backend.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;
    @Override
    public void insert(Course course) {
        courseRepository.insert(course);
    }
}
