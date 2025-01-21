package com.example.course_erp_backend.repository;

import com.example.course_erp_backend.models.mybatis.role.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface RoleRepository {
    Optional<Role> findByName(@Param("name")String name);
}
