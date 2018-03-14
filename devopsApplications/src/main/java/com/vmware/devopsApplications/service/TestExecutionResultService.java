package com.vmware.devopsApplications.service;

import com.vmware.devopsApplications.model.TestExecutionResult;
import com.vmware.devopsApplications.repository.TestExecutionResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestExecutionResultService {

    @Autowired
    private TestExecutionResultRepository testExecutionResultRepository;

    public void createTestExecutionResult(List<TestExecutionResult> testExecutionResultList){
        testExecutionResultRepository.save(testExecutionResultList);
    }
}
