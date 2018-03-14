package com.vmware.devopsApplications.utility;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Component
@Slf4j
public class JenkinsUtility implements ApplicationListener<ApplicationReadyEvent> {

    @Setter
    @Getter
    private HttpHeaders headers;

    @Getter
    @Setter
    @Value("${jenkins.url}")
    private String url;

    @Value("${jenkins.userName}")
    private String userName;

    @Value("${jenkins.password}")
    private String password;

    public JenkinsUtility() {
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        final String jenkinsUrl=url+"/crumbIssuer/api/json";
        RestTemplate restTemplate=new RestTemplate();
        headers=new HttpHeaders();
        String auth = userName + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        headers.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        HttpEntity httpHeaders=new HttpEntity(headers);
        ResponseEntity<String> res= restTemplate.exchange(jenkinsUrl, HttpMethod.GET,httpHeaders,String.class);
        String s=  res.getBody();
        try {
            JSONObject jsonObj = new JSONObject(s);
            headers.set(jsonObj.getString("crumbRequestField"), jsonObj.getString("crumb"));
        }
        catch (Exception e){
            log.error(e.getMessage(),e);
            e.printStackTrace();
        }
    }
}
