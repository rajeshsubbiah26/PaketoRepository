package com.vmware.devopsApplications.repository;

import com.vmware.devopsApplications.model.TestExecutionQueue;
import com.vmware.devopsApplications.model.TestSuiteMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TestSuiteMasterRepository extends JpaRepository<TestSuiteMaster,Integer> {

    List<TestSuiteMaster> findByTestSuiteMasterIdIn(Set<Integer> id);

    @Query(value = "SELECT testSuiteName FROM TestSuiteMaster WHERE testSuiteMasterId = ?1")
    String findTestSuiteNameById(int id);
}
