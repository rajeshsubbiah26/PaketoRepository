package com.vmware.devopsApplications.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="execution_results")
public class ExecutionResults {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_execution_results",length = 11)
    private int id;

    @Getter
    @Setter
    @Column(name = "queue_id",length = 11)
    private int queueId;


    @Getter
    @Setter
    @Column(name = "run_id",length = 450)
    private String runId;

    @Getter
    @Setter
    @Column(name = "id_testsuite",length = 11)
    private int testSuiteId;

    @Getter
    @Setter
    @Column(name = "group_info",length = 450)
    private String groupInfo;

    @Getter
    @Setter
    @Column(name = "result",length = 45)
    private String result;

    @Getter
    @Setter
    @Column(name = "link_url",length = 450)
    private String linkUrl;

    @Getter
    @Setter
    @Column(name = "report_details",length = 4500)
    private String reportDetails;


    public ExecutionResults() {
    }

    public ExecutionResults(int id, int queueId, String runId, int testSuiteId, String groupInfo, String result, String linkUrl, String reportDetails) {
        this.id = id;
        this.queueId = queueId;
        this.runId = runId;
        this.testSuiteId = testSuiteId;
        this.groupInfo = groupInfo;
        this.result = result;
        this.linkUrl = linkUrl;
        this.reportDetails = reportDetails;
    }

    @Override
    public String toString() {
        return "ExecutionResults{" +
                "id=" + id +
                ", queueId=" + queueId +
                ", runId='" + runId + '\'' +
                ", testSuiteId=" + testSuiteId +
                ", groupInfo='" + groupInfo + '\'' +
                ", result='" + result + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                '}';
    }
}






