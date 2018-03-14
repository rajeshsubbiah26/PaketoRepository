package com.vmware.devopsApplications.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration

public class SwaggerConfig {

    @Bean
    public Docket testExcutionAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vmware.devopsApplications.controller"))
                .paths(regex("/testexecution.*"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "vmwareDevops Api",
                "vmwareDevops Api for Other developers",
                "1.0",
                "Terms of Service",
                new Contact("Rajesh subbiah", "https://www.vmware.com", "rsubbiah@vmware.com"),
                "Apche Lisence version 2.0",
                "www.apache.org/licesen.html", Collections.emptyList()
        );
        return apiInfo;
    }
}
