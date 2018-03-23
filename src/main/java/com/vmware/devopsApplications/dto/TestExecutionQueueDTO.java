package com.vmware.devopsApplications.dto;

import lombok.Getter;
import lombok.Setter;

public class TestExecutionQueueDTO {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private  int queueId;

    @Getter
    @Setter
    private String browser;

    @Getter
    @Setter
    private String environment;

    @Getter
    @Setter
    private String autoDefect;

    @Getter
    @Setter
    private int testSuiteId;

}
