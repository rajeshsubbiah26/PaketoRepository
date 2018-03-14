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
    @Column(name = "status",length = 45)
    private String status;

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
    private byte autoDefect;

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
    @Column(name = "testpack_id",length = 11)
    private int testpackId;

    @Getter
    @Setter
    @Column(name = "archive_flag",length = 4)
    private byte archiveFlag;

    public TestExecutionQueue() {
    }

    public TestExecutionQueue(int id, int queueId, int testSuiteId, String status, String browser, String environment, byte autoDefect, String runTrials, int frameworkId, String frameworkParams, int testpackId, byte archiveFlag) {
        this.id = id;
        this.queueId = queueId;
        this.testSuiteId = testSuiteId;
        this.status = status;
        this.browser = browser;
        this.environment = environment;
        this.autoDefect = autoDefect;
        this.runTrials = runTrials;
        this.frameworkId = frameworkId;
        this.frameworkParams = frameworkParams;
        this.testpackId = testpackId;
        this.archiveFlag = archiveFlag;
    }

    @Override
    public String toString() {
        return "TestExecutionQueue{" +
                "id=" + id +
                ", queueId=" + queueId +
                ", testSuiteId=" + testSuiteId +
                ", status='" + status + '\'' +
                ", browser='" + browser + '\'' +
                ", environment='" + environment + '\'' +
                ", autoDefect=" + autoDefect +
                ", runTrials='" + runTrials + '\'' +
                ", frameworkId=" + frameworkId +
                ", frameworkParams='" + frameworkParams + '\'' +
                ", testpackId=" + testpackId +
                ", archiveFlag=" + archiveFlag +
                '}';
    }
}
