package com.vmware.devopsApplications.repository;

import com.vmware.devopsApplications.model.Applications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ApplicationsRepository extends JpaRepository<Applications,Integer> {

    List<Applications> findByAppIdIn(Set<Integer> id);

}
