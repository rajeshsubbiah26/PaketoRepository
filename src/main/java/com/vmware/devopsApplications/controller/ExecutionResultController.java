package com.vmware.devopsApplications.controller;

import com.vmware.devopsApplications.dto.JenkinsJsonResponseDTO;
import com.vmware.devopsApplications.service.ExecutionResultService;
import com.vmware.devopsApplications.utility.ResponseUtility;
import org.hibernate.boot.jaxb.SourceType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class ExecutionResultController {

    @Autowired
    private ResponseUtility responseUtility;

    @Autowired
    private ExecutionResultService executionResultService;

    @RequestMapping(value = "/executionResult/update/{instance}",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> updateExecutionResults(@Valid @RequestBody JenkinsJsonResponseDTO resultDetails, @PathVariable String instance ){
    Map responseMap;
    executionResultService.updateResultDetails(resultDetails,instance);
    responseMap=responseUtility.getResponseJson("success","Successfully get the data");
    return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/newExecutionResult/{instance}",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> insertExecutionResults( @RequestBody String executionResultParams,@PathVariable String instance) throws Exception{
        Map responseMap;
        executionResultService.createExecutionResults(executionResultParams,instance);
        responseMap=responseUtility.getResponseJson("success","Successfully inserted execution results");
        return new ResponseEntity<>(responseMap, HttpStatus.OK);

    }
}
