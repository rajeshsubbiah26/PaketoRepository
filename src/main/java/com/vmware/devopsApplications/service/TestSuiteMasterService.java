package com.vmware.devopsApplications.service;

import com.vmware.devopsApplications.exception.EntityNotFoundException;
import com.vmware.devopsApplications.model.TestSuiteMaster;
import com.vmware.devopsApplications.repository.TestSuiteMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class TestSuiteMasterService {

    @Autowired
    private TestSuiteMasterRepository testSuiteMasterRepository;

    public List<TestSuiteMaster> getTestSuiteMasterName(Set<Integer> id){
        log.info("Listing TestSuiteMasterDetails started for the id {}",id);
        List<TestSuiteMaster> testSuiteMaster=testSuiteMasterRepository.findByTestSuiteMasterIdIn(id);
        if(testSuiteMaster == null){
            throw new EntityNotFoundException("No test_suite_details present for the id"+id);
        }
        log.info("Listing TestSuiteMasterDetails completed for the id {}",id);
        return testSuiteMaster;
    }
}
