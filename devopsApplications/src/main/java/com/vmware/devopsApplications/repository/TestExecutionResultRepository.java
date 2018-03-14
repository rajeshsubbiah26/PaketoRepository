package com.vmware.devopsApplications.repository;

import com.vmware.devopsApplications.model.TestExecutionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestExecutionResultRepository extends JpaRepository<TestExecutionResult,Integer> {
}
