package com.example.course_erp_backend.repository;

import com.example.course_erp_backend.models.mybatis.employee.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeRepository {
    void insert(Employee employee);
}
