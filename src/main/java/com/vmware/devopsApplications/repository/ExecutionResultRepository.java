package com.vmware.devopsApplications.repository;

import com.vmware.devopsApplications.model.ExecutionResults;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionResultRepository extends JpaRepository<ExecutionResults,Integer> {

    ExecutionResults findOneByLinkUrl(String url);

}
