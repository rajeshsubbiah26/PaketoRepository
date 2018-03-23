package com.vmware.devopsApplications.dto;

import lombok.Getter;
import lombok.Setter;

public class JsonJenkinsBuildDTO {

    @Getter
    @Setter
    private String full_url;

    @Getter
    @Setter
    private Long number;

    @Getter
    @Setter
    private Long queue_id;

    @Getter
    @Setter
    private String timestamp;

    @Getter
    @Setter
    private String phase;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private Object scm;

    @Getter
    @Setter
    private String log;

    @Getter
    @Setter
    private Object artifacts;

    @Override
    public String toString() {
        return "JsonJenkinsBuildDTO{" +
                "full_url='" + full_url + '\'' +
                ", number=" + number +
                ", queue_id=" + queue_id +
                ", timestamp='" + timestamp + '\'' +
                ", phase='" + phase + '\'' +
                ", status='" + status + '\'' +
                ", url='" + url + '\'' +
                ", scm=" + scm +
                ", log='" + log + '\'' +
                ", artifacts=" + artifacts +
                '}';
    }
}
