package com.vmware.devopsApplications.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "testsuite_details")
public class TestSuiteDetails {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "testsuite_details_id",length =11)
    private int id;

    @Getter
    @Setter
    @Column(name = "id_testsuite",length = 11)
    private int testSuiteId;

    @Getter
    @Setter
    @Column(name = "ts_id",length =45 )
    private String tsId;

    @Getter
    @Setter
    @Column(name = "predecessor_tsid",length = 45)
    private String predecessorTsId;

    @Getter
    @Setter
    @Column(name = "group_info",length = 45)
    private String groupInfo;

    @Getter
    @Setter
    @Column(name = "active_flag",length = 4)
    private byte activeFlag;

    @Getter
    @Setter
    @Column(name = "selectYN",length = 4)
    private byte selectYN;

    @Getter
    @Setter
    @Column(name = "app_id",length = 11)
    private int appId;

    public TestSuiteDetails() {
    }

    public TestSuiteDetails(int id, int testsuiteId, String tsId, String predecessorTsId, String groupInfo, byte activeFlag, byte selectYN, int appId) {
        this.id = id;
        this.testSuiteId = testsuiteId;
        this.tsId = tsId;
        this.predecessorTsId = predecessorTsId;
        this.groupInfo = groupInfo;
        this.activeFlag = activeFlag;
        this.selectYN = selectYN;
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "TestSuiteDetails{" +
                "id=" + id +
                ", testSuiteId=" + testSuiteId +
                ", tsId='" + tsId + '\'' +
                ", predecessorTsId='" + predecessorTsId + '\'' +
                ", groupInfo='" + groupInfo + '\'' +
                ", archiveFlag=" + activeFlag +
                ", selectYN=" + selectYN +
                ", appId=" + appId +
                '}';
    }
}
