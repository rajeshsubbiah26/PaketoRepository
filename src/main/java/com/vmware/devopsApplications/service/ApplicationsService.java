package com.vmware.devopsApplications.service;

import com.vmware.devopsApplications.exception.EntityNotFoundException;
import com.vmware.devopsApplications.model.Applications;
import com.vmware.devopsApplications.repository.ApplicationsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ApplicationsService {

    @Autowired
    private ApplicationsRepository applicationsRepository;

    public List<Applications> getApplicationDetails(Set<Integer> id) throws EntityNotFoundException{
        log.info("Listing applicationDetails started for the id {}",id);
        List<Applications> applicationsDetails=applicationsRepository.findByAppIdIn(id);
        if(applicationsDetails == null){
            throw new EntityNotFoundException("No application details present for the id "+id);
        }
        log.info("Listing application completed for the id {}",id);
        return applicationsDetails;
    }

}
