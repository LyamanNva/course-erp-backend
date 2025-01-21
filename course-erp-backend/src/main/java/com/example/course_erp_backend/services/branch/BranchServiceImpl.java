package com.example.course_erp_backend.services.branch;

import com.example.course_erp_backend.models.mybatis.branch.Branch;
import com.example.course_erp_backend.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService{

    private final BranchRepository branchRepository;
    @Override
    public void insert(Branch branch) {
        branchRepository.insert(branch);
    }
}
