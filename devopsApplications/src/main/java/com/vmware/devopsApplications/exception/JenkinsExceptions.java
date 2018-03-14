package com.vmware.devopsApplications.exception;

import lombok.Getter;
import lombok.Setter;

public class JenkinsExceptions extends RuntimeException {
    @Setter
    @Getter
    private String message;

    @Setter
    @Getter
    private int statusCode;

    public JenkinsExceptions(String message,int statusCode){
        this.message=message;
        this.statusCode=statusCode;
    }
}
