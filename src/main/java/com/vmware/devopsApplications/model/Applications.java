package com.vmware.devopsApplications.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "applications")
public class Applications {

    @Getter
    @Setter
    @Id
    @Column(name = "id_apps",length = 11)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int appId;

    @Getter
    @Setter
    @Column(name = "app_name",length = 45)
    private String appName;

    @Getter
    @Setter
    @Column(name = "active_flag",length = 4)
    private byte activeFlag;

}
