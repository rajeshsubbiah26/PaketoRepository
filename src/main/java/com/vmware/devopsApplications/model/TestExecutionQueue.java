package com.vmware.devopsApplications.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "testexecution_queue")
public class TestExecutionQueue {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_testexecution_queue",length = 11)
    private int id;

    @Getter
    @Setter
    @Column(name = "queue_id",length = 11)
    private int queueId;

    @Getter
    @Setter
    @Column(name = "testsuite_id",length = 11)
    private int testSuiteId;


    @Getter
    @Setter
    @Column(name = "browser",length = 45)
    private String browser;

    @Getter
    @Setter
    @Column(name="environment",length = 45)
    private String environment;


    @Getter
    @Setter
    @Column(name = "auto_defect",length = 4)
    private String autoDefect;

    @Getter
    @Setter
    @Column(name ="run_trials",length = 4)
    private String runTrials;

    @Getter
    @Setter
    @Column(name = "framework_id",length = 11)
    private int frameworkId;

    @Getter
    @Setter
    @Column(name = "framework_params",length = 256)
    private String frameworkParams;


    @Getter
    @Setter
    @Column(name = "active_flag",length = 4)
    private byte activeFlag;

    public TestExecutionQueue() {
    }

    public TestExecutionQueue(int id, int queueId, int testSuiteId,  String browser, String environment, String autoDefect, String runTrials, int frameworkId, String frameworkParams, byte activeFlag) {
        this.id = id;
        this.queueId = queueId;
        this.testSuiteId = testSuiteId;
        this.browser = browser;
        this.environment = environment;
        this.autoDefect = autoDefect;
        this.runTrials = runTrials;
        this.frameworkId = frameworkId;
        this.frameworkParams = frameworkParams;
        this.activeFlag = activeFlag;
    }

    @Override
    public String toString() {
        return "TestExecutionQueue{" +
                "id=" + id +
                ", queueId=" + queueId +
                ", testSuiteId=" + testSuiteId +
                ", browser='" + browser + '\'' +
                ", environment='" + environment + '\'' +
                ", autoDefect=" + autoDefect +
                ", runTrials='" + runTrials + '\'' +
                ", frameworkId=" + frameworkId +
                ", frameworkParams='" + frameworkParams + '\'' +
                ", activeFlag=" + activeFlag +
                '}';
    }
}
