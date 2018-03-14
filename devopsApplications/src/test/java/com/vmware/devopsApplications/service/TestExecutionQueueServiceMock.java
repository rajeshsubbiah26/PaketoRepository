package com.vmware.devopsApplications.service;

import com.vmware.devopsApplications.model.TestExecutionQueue;
import com.vmware.devopsApplications.model.TestSuiteDetails;
import com.vmware.devopsApplications.repository.TestExecutionQueueRepository;
import com.vmware.devopsApplications.repository.TestSuiteDetailsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestExecutionQueueServiceMock {

    @Mock
    private TestExecutionQueueRepository testExecutionQueueRepository;

    @Mock
    private TestSuiteDetailsRepository testsuiteDetailsRepository;

    @InjectMocks
    private TestExecutionQueueService testExecutionQueueService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test Single Test Execution Id & same testsuide id with multiple group
     * */

    @Test
    public void checkMockDataForOneTestSuiteWithOneGroup(){

        ArrayList<Integer> arrayList=new ArrayList<>();
        List<MultiValueMap<String,String>> multiValueMaps=new ArrayList<>();
        arrayList.add(111);
        List<TestExecutionQueue> testExecutionQueues = new ArrayList<>();
        testExecutionQueues.add(new TestExecutionQueue(5, 2000, 111, "NOT STARTED", "Chrome", "UAT", (byte) 1, "1", 1, "test=EnvEnv", 1, (byte) 1));
        List<TestSuiteDetails> testsuiteDetails=new ArrayList<>();
        testsuiteDetails.add(new TestSuiteDetails(9999, 111, "1", "1A", "group1", (byte) 0, (byte) 1, 1));
        testsuiteDetails.add(new TestSuiteDetails(99, 111, "2", "3A", "group2", (byte) 0, (byte) 1, 1));
        testsuiteDetails.add(new TestSuiteDetails(9, 111, "2", "2A", "group3", (byte) 0, (byte) 1, 1));
        when(testExecutionQueueRepository.findByQueueId(2000)).thenReturn( testExecutionQueues);
        when(testsuiteDetailsRepository.findByTestSuiteIdIn(arrayList)).thenReturn(testsuiteDetails);

        for (TestExecutionQueue testExecutionQueue : testExecutionQueues) {
            for (TestSuiteDetails testsuiteDetailsList : testsuiteDetails) {
                MultiValueMap<String, String> map = testExecutionQueueService.buildJenkinsJson(testExecutionQueue, testsuiteDetailsList);
                if (map.size() > 0) {
                    multiValueMaps.add(map);
                }
            }
        }
        Assert.assertEquals(multiValueMaps.size(),3);
        for (MultiValueMap<String, String> multiValueMaps1:multiValueMaps){
            Assert.assertTrue(multiValueMaps1.containsKey("BROWSER"));
            Assert.assertTrue(multiValueMaps1.containsKey("ENVIRONMENT"));
            Assert.assertTrue(multiValueMaps1.containsKey("APPID"));
            Assert.assertTrue(multiValueMaps1.containsKey("AUTODEFECT"));
            Assert.assertTrue(multiValueMaps1.containsKey("FRAMEWORKID"));
            Assert.assertTrue(multiValueMaps1.containsKey("QUEUEID"));
            Assert.assertTrue(multiValueMaps1.containsKey("TESTSUITEID"));
            Assert.assertTrue(multiValueMaps1.containsKey("GROUPINFO"));
            Assert.assertTrue(multiValueMaps1.containsKey("RUNTRAILS"));
        }
    }

    /**
     * Multiple Test ExecutionId With multiple testsuiteId and single group
     * */
    @Test
    public void checkMockDataForMultipleTestSuiteWithOneGroup(){
            List<MultiValueMap<String,String>> multiValueMaps=new ArrayList<>();
            List<TestExecutionQueue> testExecutionQueues = new ArrayList<>();
            testExecutionQueues.add(new TestExecutionQueue(5, 2000, 111, "NOT STARTED", "Chrome", "UAT", (byte) 1, "1", 1, "test=EnvEnv", 1, (byte) 1));
            testExecutionQueues.add(new TestExecutionQueue(6, 2000, 112, "NOT STARTED", "Chrome", "UAT", (byte) 1, "1", 1, "test=TestMe", 1, (byte) 1));
            List<TestSuiteDetails> testsuiteDetails=new ArrayList<>();
            testsuiteDetails.add(new TestSuiteDetails(9999, 111, "1", "1A", "group1", (byte) 0, (byte) 1, 1));
            testsuiteDetails.add(new TestSuiteDetails(2900, 112, "2", "2A", "group1", (byte) 1, (byte) 0, 1));

        for (TestExecutionQueue testExecutionQueue : testExecutionQueues) {
            for (TestSuiteDetails testsuiteDetailsList : testsuiteDetails) {
                MultiValueMap<String, String> map = testExecutionQueueService.buildJenkinsJson(testExecutionQueue, testsuiteDetailsList);
                if (map.size() > 0) {
                    multiValueMaps.add(map);
                }
            }
        }
        Assert.assertEquals(multiValueMaps.size(),2);
        for (MultiValueMap<String, String> multiValueMaps1:multiValueMaps){
            Assert.assertTrue(multiValueMaps1.containsKey("BROWSER"));
            Assert.assertTrue(multiValueMaps1.containsKey("ENVIRONMENT"));
            Assert.assertTrue(multiValueMaps1.containsKey("APPID"));
            Assert.assertTrue(multiValueMaps1.containsKey("AUTODEFECT"));
            Assert.assertTrue(multiValueMaps1.containsKey("FRAMEWORKID"));
            Assert.assertTrue(multiValueMaps1.containsKey("QUEUEID"));
            Assert.assertTrue(multiValueMaps1.containsKey("TESTSUITEID"));
            Assert.assertTrue(multiValueMaps1.containsKey("GROUPINFO"));
            Assert.assertTrue(multiValueMaps1.containsKey("RUNTRAILS"));
        }
    }

    /**
     * multiple test suite id with multiple group
     * */

    @Test
    public void checkMockDataForMultipleTestSuiteWithMultipleGroup(){
        List<MultiValueMap<String,String>> multiValueMaps=new ArrayList<>();
        List<TestExecutionQueue> testExecutionQueues = new ArrayList<>();
        testExecutionQueues.add(new TestExecutionQueue(5, 2000, 111, "NOT STARTED", "Chrome", "UAT", (byte) 1, "1", 1, "test=testMeThe details", 1, (byte) 1));
        testExecutionQueues.add(new TestExecutionQueue(6, 2000, 112, "NOT STARTED", "Chrome", "UAT", (byte) 1, "1", 1, "test=Tetsing the details", 1, (byte) 1));

        List<TestSuiteDetails> testsuiteDetails=new ArrayList<>();
        testsuiteDetails.add(new TestSuiteDetails(9999, 111, "1", "1A", "group1", (byte) 0, (byte) 1, 1));
        testsuiteDetails.add(new TestSuiteDetails(2900, 111, "2", "2A", "group2", (byte) 1, (byte) 0, 1));
        testsuiteDetails.add(new TestSuiteDetails(9977, 112, "3", "3A", "group4", (byte) 0, (byte) 0, 1));
        testsuiteDetails.add(new TestSuiteDetails(2800, 112, "4", "4A", "group3", (byte) 1, (byte) 1, 1));
        testsuiteDetails.add(new TestSuiteDetails(2090, 110, "8", "8A", "group6", (byte) 1, (byte) 1, 1));

        for (TestExecutionQueue testExecutionQueue : testExecutionQueues) {
            for (TestSuiteDetails testsuiteDetailsList : testsuiteDetails) {
                MultiValueMap<String, String> map = testExecutionQueueService.buildJenkinsJson(testExecutionQueue, testsuiteDetailsList);
                if (map.size() > 0) {
                    multiValueMaps.add(map);
                }
            }
        }
        Assert.assertEquals(multiValueMaps.size(),4);
        for (MultiValueMap<String, String> multiValueMaps1:multiValueMaps){
            Assert.assertTrue(multiValueMaps1.containsKey("BROWSER"));
            Assert.assertTrue(multiValueMaps1.containsKey("ENVIRONMENT"));
            Assert.assertTrue(multiValueMaps1.containsKey("APPID"));
            Assert.assertTrue(multiValueMaps1.containsKey("AUTODEFECT"));
            Assert.assertTrue(multiValueMaps1.containsKey("FRAMEWORKID"));
            Assert.assertTrue(multiValueMaps1.containsKey("QUEUEID"));
            Assert.assertTrue(multiValueMaps1.containsKey("TESTSUITEID"));
            Assert.assertTrue(multiValueMaps1.containsKey("GROUPINFO"));
            Assert.assertTrue(multiValueMaps1.containsKey("RUNTRAILS"));
        }
    }


    /**
     *Invalid Test suite id
     *
     * */
    @Test
    public void checkMockDataWithInvalidTestSuiteId(){
        List<MultiValueMap<String,String>> multiValueMaps=new ArrayList<>();
        List<TestExecutionQueue> testExecutionQueues = new ArrayList<>();
        testExecutionQueues.add(new TestExecutionQueue(5, 200, 111, "NOT STARTED", "Chrome", "UAT", (byte) 1, "1", 1, "test=testMeThe details", 1, (byte) 1));
        List<TestSuiteDetails> testsuiteDetails=new ArrayList<>();
        testsuiteDetails.add(new TestSuiteDetails(9999, 11, "1", "1A", "group1", (byte) 0, (byte) 1, 1));
        testsuiteDetails.add(new TestSuiteDetails(2900, 1, "2", "2A", "group2", (byte) 1, (byte) 0, 1));
        testsuiteDetails.add(new TestSuiteDetails(9977, 12, "3", "3A", "group4", (byte) 0, (byte) 0, 1));
        testsuiteDetails.add(new TestSuiteDetails(2800, 10, "4", "4A", "group3", (byte) 1, (byte) 1, 1));

        for (TestExecutionQueue testExecutionQueue : testExecutionQueues) {
            for (TestSuiteDetails testsuiteDetailsList : testsuiteDetails) {
                MultiValueMap<String, String> map = testExecutionQueueService.buildJenkinsJson(testExecutionQueue, testsuiteDetailsList);
                if (map.size() > 0) {
                    multiValueMaps.add(map);
                }
            }
        }
        Assert.assertEquals(multiValueMaps.size(),0);
    }

}
