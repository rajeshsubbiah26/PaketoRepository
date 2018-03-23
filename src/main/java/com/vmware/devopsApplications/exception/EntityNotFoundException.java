package com.vmware.devopsApplications.exception;

import lombok.Getter;
import lombok.Setter;

public class EntityNotFoundException extends RuntimeException {
    @Setter
    @Getter
    private String message;

    public EntityNotFoundException(String message){
        this.message=message;
    }
}
