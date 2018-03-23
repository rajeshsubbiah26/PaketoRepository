package com.vmware.devopsApplications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DevopsApplications {

	public static void main(String[] args) {
		SpringApplication.run(DevopsApplications.class, args);
	}
}
