package com.vmware.devopsApplications.repository;

import com.vmware.devopsApplications.model.TestExecutionQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestExecutionQueueRepository extends JpaRepository<TestExecutionQueue,Integer> {
    List<TestExecutionQueue> findByQueueId(int id);


    @Query(value = "SELECT * FROM testexecution_queue WHERE queue_id = ?1 AND testsuite_id IN (?2)", nativeQuery = true)
    List<TestExecutionQueue> findByIdANDTestSuite(int queryId,List<Integer> testSuiteId);

}
