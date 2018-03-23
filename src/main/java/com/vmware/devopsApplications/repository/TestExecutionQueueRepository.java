package com.vmware.devopsApplications.repository;

import com.vmware.devopsApplications.model.TestExecutionQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;

public interface TestExecutionQueueRepository extends JpaRepository<TestExecutionQueue,Integer> {
    List<TestExecutionQueue> findByQueueId(int id);


    @Query(value = "SELECT * FROM testexecution_queue WHERE queue_id = ?1 AND testsuite_id IN (?2)", nativeQuery = true)
    List<TestExecutionQueue> findByIdANDTestSuite(int queryId,List<Integer> testSuiteId);

    @Query(value="SELECT t.id as id ,t.queueId as queueId,t.testSuiteId as testSuiteId ,t.browser as browser,t.environment as environment,t.autoDefect as autoDefect FROM TestExecutionQueue t where t.id=?1")
    List<HashMap<?,?>> findTestExecutionBuildDetails(int id);

    @Query(value = "Select tq.id_testexecution_queue,tq.queue_id,tq.testsuite_id,tq.browser,tq.environment,tq.auto_defect,td.group_info,td.app_id,app.app_name,tm.testsuitename from testexecution_queue tq,testsuite_details td,applications app ,testsuite_master tm where tq.queue_id=?1 AND tq.testsuite_id = td.id_testsuite AND app.id_apps=td.app_id AND tm.id_testsuite_master=td.id_testsuite;",nativeQuery = true)
    List<Object[]> findTestExecutionParams(int id);
}
