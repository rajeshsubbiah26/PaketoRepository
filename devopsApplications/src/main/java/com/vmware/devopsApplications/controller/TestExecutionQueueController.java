package com.vmware.devopsApplications.controller;

import com.vmware.devopsApplications.service.TestExecutionQueueService;
import com.vmware.devopsApplications.utility.ResponseUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@Api(value = "Test Execution REST Endpoint",description = "Perform all the TestExecution operations")
public class TestExecutionQueueController
{

    @Autowired
    private TestExecutionQueueService testExecutionQueueService;

    @Autowired
    private ResponseUtility responseUtility;


    /**
     *fetch the details and start the build in jenkins
     * @param request
     * @param id (here id is nothing but the queueId)
     * @return
     */

    @ApiOperation(value="Fetch all associated testsuit details and start test execution in jenkins")
    @ApiResponses(value = {
            @ApiResponse(code=404,message="Queue_is associated  this id/No testsuite present for this id"),
            @ApiResponse(code=200,message = "Test execution started for the queue_id received from user : "),
            @ApiResponse(code=500,message = "Internal Server error/Jenkins Server is down")
    })
    @RequestMapping(value = "/testexecution/start/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> startTestExecutionWithParameters(@PathVariable int id) throws Exception
    {
        Map responseMap;
        List<Integer> testExecution=testExecutionQueueService.startTestExecution(id);
        if(testExecution.size()>0)
        {
            responseMap = responseUtility.getResponseJson("fail", "Not able to start the test execution for queue_id : ");
        }else
        {
            responseMap = responseUtility.getResponseJson("success", "Successfully started the test executions for queue_id : ");
        }
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
