package com.vmware.devopsApplications.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name= "test_execution_results")
public class TestExecutionResult {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "auto_id",length = 11)
    private int autoId;

    @Getter
    @Setter
    @Column(name = "queue_id",length = 11)
    private int queueId;


    @Getter
    @Setter
    @Column(name = "run_id",length = 256)
    private String runId;

    @Getter
    @Setter
    @Column(name = "testsuite_id",length = 11)
    private int testSuiteId;

    @Getter
    @Setter
    @Column(name = "grp_id",length = 45)
    private String groupId;

    @Getter
    @Setter
    @Column(name = "result",length = 45)
    private String result;

    @Getter
    @Setter
    @Column(name = "link_url",length = 500)
    private String linkUrl;

    public TestExecutionResult() {
    }

    public TestExecutionResult(int autoId, int queueId, String runId, int testSuiteId, String groupId, String result, String linkUrl) {
        this.autoId = autoId;
        this.queueId = queueId;
        this.runId = runId;
        this.testSuiteId = testSuiteId;
        this.groupId = groupId;
        this.result = result;
        this.linkUrl = linkUrl;
    }

    @Override
    public String toString() {
        return "TestExecutionResult{" +
                "autoId=" + autoId +
                ", queueId=" + queueId +
                ", runId='" + runId + '\'' +
                ", testSuiteId=" + testSuiteId +
                ", groupId='" + groupId + '\'' +
                ", result='" + result + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                '}';
    }
}
