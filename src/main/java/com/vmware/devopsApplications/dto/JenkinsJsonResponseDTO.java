package com.vmware.devopsApplications.dto;


import lombok.Getter;
import lombok.Setter;

public class JenkinsJsonResponseDTO {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String display_name;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private JsonJenkinsBuildDTO build;

    @Override
    public String toString() {
        return "JenkinsJsonResponseDTO{" +
                "name='" + name + '\'' +
                ", display_name='" + display_name + '\'' +
                ", url='" + url + '\'' +
                ", build=" + build +
                '}';
    }
}
