package com.example.course_erp_backend.services.employee;

import com.example.course_erp_backend.models.mybatis.employee.Employee;
import com.example.course_erp_backend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;
    @Override
    public void insert(Employee employee) {
        employeeRepository.insert(employee);

    }
}
