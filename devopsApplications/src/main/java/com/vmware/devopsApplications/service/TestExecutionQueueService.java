package com.vmware.devopsApplications.service;
import com.vmware.devopsApplications.exception.JenkinsExceptions;
import com.vmware.devopsApplications.exception.EntityNotFoundException;
import com.vmware.devopsApplications.model.TestExecutionQueue;
import com.vmware.devopsApplications.model.TestExecutionResult;
import com.vmware.devopsApplications.model.TestSuiteDetails;
import com.vmware.devopsApplications.repository.TestExecutionQueueRepository;
import com.vmware.devopsApplications.utility.JenkinsUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class TestExecutionQueueService {

    @Autowired
    private TestExecutionQueueRepository testExecutionQueueRepository;

    @Autowired
    private TestSuiteDetailsService testsuiteDetailsService;

    @Autowired
    private JenkinsUtility jenkinsUtility;

    @Autowired
    private TestExecutionResultService testExecutionResultService;

    @Value("${jenkins.url}")
    private String url;

    @Value("${jenkins.jobName}")
    private String jobName;


    /**
     * fetch all the details to start test case
     * it also update the status of queue
     * call the function which start the jenkins build
     * json format will be
     * [{
     * "browser":"chrome",
     * "environment":"uat",
     * "appid":1,
     * "autodefect":1,
     * "frameworkid":1,
     * "queueid":1,
     * "testsuiteid":1,
     * "groupinfo":"group1",
     * "runtrails":"1"
     * },
     * {
     * "browser":"chrome",
     * "environment":"uat",
     * "appid":1,
     * "autodefect":0,
     * "frameworkid":2,
     * "queueid":1,
     * "testsuiteid":2,
     * "groupinfo":"group2",
     * "runtrails":"1"
     * }]
     */

    @Transactional
    public List<Integer> startTestExecution(int id) throws Exception{
        log.info("Test Execution started for queue_id : {}", id);
        ResponseEntity res;
        List checkAllBuilds = new ArrayList();
        List<TestExecutionQueue> testExecution = new ArrayList<>();
        ArrayList<Integer> testSuiteList = new ArrayList();
        List<TestExecutionResult> testExecutionResults=new ArrayList<>();

        try {
            List<TestExecutionQueue> testExecutionQueues=this.getTestExecutionDetailsBasedOnQueueId(id);
            testExecutionQueues.forEach(testExecutionQueue -> {
                testSuiteList.add(testExecutionQueue.getTestSuiteId());
            });
            List<TestSuiteDetails> testSuiteDetails=this.getAllTestSuiteDetails(testSuiteList);
            String RunIDTimeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            for (TestExecutionQueue testExecutionQueue : testExecutionQueues) {   //Loop to build the json
                String status = "STARTED";
                    for (TestSuiteDetails testsuiteDetailsList : testSuiteDetails) {
                    MultiValueMap<String, String> map = buildJenkinsJson(testExecutionQueue, testsuiteDetailsList); //Form the json
                    TestExecutionResult testExecutionResult=new TestExecutionResult();
                    if (map.size() > 0) {
                        res = startJenkinsBuild(map);                                  //Start the jenkins builds
                        if (!res.getStatusCode().is2xxSuccessful()) {
                            status = "FAIL";
                            checkAllBuilds.add(testExecutionQueue.getTestSuiteId());
                        }
                        //log.info("Map from Jenkins {}", map);  //added on 12 march 2018
                        //log.info("Res from Jenkins {}", res);  //added on 12 march 2018
                        status = "COMPLETED";

                        String result = "InProgress";
                        testExecutionResult.setGroupId(map.get("GROUPINFO").get(0));
                        testExecutionResult.setQueueId(Integer.parseInt(map.get("QUEUEID").get(0)));
                        testExecutionResult.setRunId(map.get("QUEUEID").get(0)+RunIDTimeStamp);
                        testExecutionResult.setTestSuiteId(Integer.parseInt(map.get("TESTSUITEID").get(0)));
                        testExecutionResult.setResult(result);  //Added on 12 march 2018
                        testExecutionResults.add(testExecutionResult);
                    }
                }
                //testExecutionResult.setResult(status);
                testExecutionQueue.setStatus(status);
                testExecution.add(testExecutionQueue);
            }
            testExecutionResultService.createTestExecutionResult(testExecutionResults);
            testExecutionQueueRepository.save(testExecutionQueues);               //Update the queue execution row
            log.info("Test Execution completed for queue_id : {}", id);
            return checkAllBuilds;
        } catch (Exception e) {
            log.error("Error in startTestExecution ",e);
            e.printStackTrace();
            if (e instanceof ResourceAccessException) {
                throw new JenkinsExceptions("Jenkins Server is down", 500);
            }
            throw e;
        }
    }

    /**
     * REST call to jenkins and start the jenkins build
     */
    private ResponseEntity<?> startJenkinsBuild(MultiValueMap<String, String> map) {
        //log.info(" Jenkins job execution started for the set : {}", map);
        log.info(" Jenkins job : {} execution started for the set : {}",jobName, map);
        ResponseEntity res = null;
        HttpHeaders headers = jenkinsUtility.getHeaders();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        res = restTemplate.exchange(url + "/job/" + jobName + "/buildWithParameters", HttpMethod.POST, httpEntity, String.class);
        log.info(" Jenkins job : {} execution completed for the set : {}",jobName, map);  //added on 12 march 2018

        return res;
    }


    public MultiValueMap<String, String> buildJenkinsJson(TestExecutionQueue testExecutionQueue, TestSuiteDetails testSuiteDetails) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        if (testExecutionQueue.getTestSuiteId() == testSuiteDetails.getTestSuiteId()) {
            map.add("BROWSER", testExecutionQueue.getBrowser());
            map.add("ENVIRONMENT", testExecutionQueue.getEnvironment());
            map.add("APPID", String.valueOf(testSuiteDetails.getAppId()));
            map.add("AUTODEFECT", String.valueOf(testExecutionQueue.getAutoDefect()));
            map.add("FRAMEWORKID", String.valueOf(testExecutionQueue.getFrameworkId()));
            map.add("QUEUEID", String.valueOf(testExecutionQueue.getQueueId()));
            map.add("TESTSUITEID", String.valueOf(testExecutionQueue.getTestSuiteId()));
            map.add("GROUPINFO", testSuiteDetails.getGroupInfo());
            map.add("RUNTRIALS", testExecutionQueue.getRunTrials());
            //map.add("RESULT", testExecutionResults.getResult());
        }
        return map;
    }

    public List<TestExecutionQueue> getTestExecutionDetailsBasedOnQueueId(int id) throws EntityNotFoundException {
        log.info("Listing of Test execution queue details for queue_id : {} started", id);
        List<TestExecutionQueue> testExecutionQueues = testExecutionQueueRepository.findByQueueId(id);
        //log.info("Started Listing Test execution queue details111 for testExecutionQueues{} ",testExecutionQueues);
        if (testExecutionQueues.size() <= 0){
            throw new EntityNotFoundException("No Queue is associated with this id");
        }
        log.info("Listing of Test execution queue details for queues_id : {} completed with details as : {} ", id, testExecutionQueues);
        return testExecutionQueues;
    }


    public List<TestSuiteDetails> getAllTestSuiteDetails(List<Integer> testSuiteList) throws EntityNotFoundException {
        //log.info("Started Listing Test Suite details");
        log.info("Listing of Test Suite details for testsuite_id : {} started", testSuiteList);
        List<TestSuiteDetails> testSuiteDetails = testsuiteDetailsService.getTestSuiteDetailsByTestSuiteIdList(testSuiteList);
        if (testSuiteDetails.size() <= 0){
            throw new EntityNotFoundException("No testsuite present for this id");
        }
        //log.info("Completed Listing Test Suite details");
        log.info("Listing of Test Suite details for testsuite_id : {} completed with details as : {} ",testSuiteList, testSuiteDetails);
        return testSuiteDetails;
    }
}

