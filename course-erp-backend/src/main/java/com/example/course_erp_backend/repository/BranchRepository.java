package com.example.course_erp_backend.repository;

import com.example.course_erp_backend.models.mybatis.branch.Branch;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BranchRepository {
    void insert(Branch branch);
}
