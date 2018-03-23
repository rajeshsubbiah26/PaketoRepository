package com.vmware.devopsApplications.service;

import com.vmware.devopsApplications.dto.JenkinsJsonResponseDTO;
import com.vmware.devopsApplications.exception.EntityNotFoundException;
import com.vmware.devopsApplications.model.ExecutionResults;
import com.vmware.devopsApplications.repository.ExecutionResultRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ExecutionResultService {

    @Autowired
    private ExecutionResultRepository executionResultRepository;

    @Value("${jenkins.vmUrl}")
    private String vmUrl;

    @Value("${jenkins.dockerUrl}")
    private String dockerUrl;

    @Transactional
      public void updateResultDetails(JenkinsJsonResponseDTO jenkinsJsonResponseDTO,String instance){
        log.info("Started updating ExecutionResults with the status {} and url is {}",jenkinsJsonResponseDTO.getBuild(),jenkinsJsonResponseDTO.getBuild().getFull_url());
       String url;
       if(instance.equalsIgnoreCase("vm")){
           url=vmUrl;
       }else{
           url=dockerUrl;
       }
        String buildUrl=url+"/"+jenkinsJsonResponseDTO.getUrl()+jenkinsJsonResponseDTO.getBuild().getNumber()+"/";
          System.out.println(buildUrl);
        ExecutionResults executionResults= executionResultRepository.findOneByLinkUrl(buildUrl);
        System.out.println(executionResults);
        if (executionResults == null){
            throw new EntityNotFoundException("No link-url present for this link"+buildUrl);
        }
        executionResults.setResult(jenkinsJsonResponseDTO.getBuild().getStatus());
        executionResultRepository.save(executionResults);
        log.info("Completed updating ExecutionResults with the status {} and url is {}",jenkinsJsonResponseDTO.getBuild(),jenkinsJsonResponseDTO.getBuild().getFull_url());
    }

    @Transactional
    public void createExecutionResults(String executionResultParams,String instance) throws Exception{
        log.info("Started inserting data in executionResults");
        String url;
        if(instance.equalsIgnoreCase("vm")){
            url=vmUrl;
        }else{
            url=dockerUrl;
        }
        String runIDTimeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        ExecutionResults executionResults=new ExecutionResults();
        JSONObject jsonObject=new JSONObject(executionResultParams);
        JSONObject testParams=new JSONObject((String) jsonObject.getJSONObject("build").getJSONObject("parameters").get("JSONParams"));
        executionResults.setLinkUrl(url+"/"+jsonObject.getJSONObject("build").get("url"));
        executionResults.setGroupInfo((String) testParams.get("GroupInfo"));
        executionResults.setResult("PENDING");
        executionResults.setQueueId(Integer.valueOf(testParams.get("QueueId").toString()));
        executionResults.setRunId(testParams.get("QueueId")+runIDTimeStamp);
        executionResults.setTestSuiteId(Integer.valueOf(testParams.get("TestSuiteId").toString()));
        executionResultRepository.save(executionResults);
        log.info("Inserting data in execution results completed");
    }
}
