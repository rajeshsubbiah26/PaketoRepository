package com.vmware.devopsApplications.service;

import com.vmware.devopsApplications.exception.EntityNotFoundException;
import com.vmware.devopsApplications.model.*;
import com.vmware.devopsApplications.repository.TestExecutionQueueRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;


@Service
@Slf4j
public class TestExecutionQueueService {

    @Autowired
    private TestExecutionQueueRepository testExecutionQueueRepository;

    @Autowired
    private TestSuiteDetailsService testsuiteDetailsService;

    @Autowired
    private JenkinsServices jenkinsServices;

    @Autowired
    private ExecutionResultService executionResultService;

    @Autowired
    private ApplicationsService applicationsService;

    @Autowired
    private TestSuiteMasterService testSuiteMasterService;

    @Value("${jenkins.vmUrl}")
    private String vmUrl;

    @Value("${jenkins.vmJobName}")
    private String vmJobName;

    @Value("${jenkins.dockerUrl}")
    private String dockerUrl;

    @Value("${jenkins.dockerJobName}")
    private String dockerJobName;




    /**
     * fetch all the details to start test case
     * it also update the status of queue
     * call the function which start the jenkins build
     * json format will be
     * {
     * "browser":"chrome",
     * "environment":"uat",
     * "appid":1,
     * "autodefect":1,
     * "frameworkid":1,
     * "queueid":1,
     * "testsuiteid":1,
     * "groupinfo":"group1",
     * "runtrails":"1"
     * }
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
     * }
     */

    @Transactional
    public List<String> startTestExecutionBuild(int id,String instance) throws Exception{
        log.info("startTestExecution function Started for the Queue_Id {}",id);
        ResponseEntity res;
        List<String> checkAllBuilds = new ArrayList();
        List<Object[]> objects=testExecutionQueueRepository.findTestExecutionParams(id);
        if(objects.size()<0){
            throw new EntityNotFoundException("No data present for the queue id "+id);
        }
        List<Map<String, String>> jenkinsBuildParameters=this.buildJenkinsJson(objects);
        for (Map<String, String> jenkinsBuildParams:jenkinsBuildParameters){
            MultiValueMap<String, String> buildParams = new LinkedMultiValueMap<>();
            buildParams.add("JSONParams", String.valueOf(new JSONObject(jenkinsBuildParams)));
            res=this.startJenkinsBuild(buildParams,instance);
            if (!res.getStatusCode().is2xxSuccessful()) {
               checkAllBuilds.add(jenkinsBuildParams.get("TestSuiteId"));
            }
        }
        log.info("startTestExecution Completed for the Queue_Id {}",id);
        return checkAllBuilds;
    }

    /**
     * REST call to jenkins and start the jenkins build
     */

    private List<Map<String, String>> buildJenkinsJson(List<Object[]> buildDetails) {
        List<Map<String,String>> jenkinsBuildData=new ArrayList<>();
        buildDetails.forEach(objects1 -> {
            Map<String,String>  map =new HashMap<>();
            map.put("Browser", String.valueOf(objects1[3]));
            map.put("TestEnvironment",String.valueOf(objects1[4]));
            map.put("AppId", String.valueOf(objects1[7]));
            map.put("AutoDefect",String.valueOf(objects1[5]));
            map.put("QueueId", String.valueOf(objects1[1]));
            map.put("TestSuiteId", String.valueOf(objects1[2]));
            map.put("GroupInfo", String.valueOf(objects1[6]));
            map.put("ProjectName", "Production");
            map.put("SuiteName", String.valueOf(objects1[9]));
            map.put("AppName", String.valueOf(objects1[8]));
            jenkinsBuildData.add(map);
        });

        return jenkinsBuildData;
    }


    private ResponseEntity<?> startJenkinsBuild(MultiValueMap<String, String> map,String instance) throws Exception {
        log.info("Triggering jenkins build in {} with the parameters {} started",instance,map);
        String url,jobName;
        if(instance.equalsIgnoreCase("vm")){
            url=vmUrl;
            jobName=vmJobName;
        }
        else{
            url=dockerUrl;
            jobName=dockerJobName;
        }
        System.out.println(url + "/job/" + jobName + "/buildWithParameters");
        ResponseEntity res = null;
        HttpHeaders headers = jenkinsServices.getJenkinsHeaders(instance);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        res = restTemplate.exchange(url + "/job/" + jobName + "/buildWithParameters", HttpMethod.POST, httpEntity, String.class);
        log.info("Triggering jenkins build in {} with the parameters {} completed",instance,map);  //added on 12 march 2018
        return res;
    }

}



