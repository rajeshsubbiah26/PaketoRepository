package com.vmware.devopsApplications.repository;

import com.vmware.devopsApplications.model.TestSuiteDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TestSuiteDetailsRepository extends JpaRepository<TestSuiteDetails,Integer> {

    List<TestSuiteDetails> findByTestSuiteIdIn(Set<Integer> id);


    List<TestSuiteDetails> findById(int id);
}
