package com.vmware.devopsApplications.service;

import com.vmware.devopsApplications.model.TestSuiteDetails;
import com.vmware.devopsApplications.repository.TestSuiteDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestSuiteDetailsService {

    @Autowired
    private TestSuiteDetailsRepository testsuiteDetailsRepository;

    public List<TestSuiteDetails> getTestSuiteDetailsByTestSuiteIdList(List<Integer> testSuiteId){
        List<TestSuiteDetails> testsuiteDetails=testsuiteDetailsRepository.findByTestSuiteIdIn(testSuiteId);
        return testsuiteDetails;
    }

}
