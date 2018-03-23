package com.vmware.devopsApplications.model;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "testsuite_master")
public class TestSuiteMaster {

    @Getter
    @Setter
    @Id
    @Column(name = "id_testsuite_master",length = 11)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int testSuiteMasterId;

    @Getter
    @Setter
    @Column(name="testsuitename",length = 45)
    private String testSuiteName;

    @Getter
    @Setter
    @Column(name = "testsuite_desc",length = 45)
    private String testSuiteDesc;

    @Getter
    @Setter
    @Column(name = "id_framework",length = 11)
    private int frameworkId;

    @Getter
    @Setter
    @Column(name = "active_flag",length = 4)
    private byte activeFlag;

    @Getter
    @Setter
    @Column(name = "id_projects",length = 11)
    private int projectsId;
}
