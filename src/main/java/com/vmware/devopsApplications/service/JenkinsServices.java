package com.vmware.devopsApplications.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Service
@Slf4j
public class JenkinsServices {

    @Value("${jenkins.vmUrl}")
    private String vmUrl;

    @Value("${jenkins.vmUserName}")
    private String vmUserName;

    @Value("${jenkins.vmPassword}")
    private String vmPassword;

    @Value("${jenkins.dockerUrl}")
    private String dockerUrl;

    @Value("${jenkins.dockerUserName}")
    private String dockerUserName;

    @Value("${jenkins.dockerPassword}")
    private String dockerPassword;


    public HttpHeaders getJenkinsHeaders(String instance) throws Exception {
        String url,userName,password,jenkinsCrumbUrl;
        if(instance.equalsIgnoreCase("vm")){
            url=vmUrl;
            userName=vmUserName;
            password=vmPassword;
        }
        else{
            url=dockerUrl;
            userName=dockerUserName;
            password=dockerPassword;
        }
        
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        String auth = userName + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        headers.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        HttpEntity httpHeaders=new HttpEntity(headers);
        if(instance.equalsIgnoreCase("vm")) {
        	jenkinsCrumbUrl=url+"/crumbIssuer/api/json";
        ResponseEntity<String> res= restTemplate.exchange(jenkinsCrumbUrl, HttpMethod.GET,httpHeaders,String.class);
        String s=  res.getBody();
            JSONObject jsonObj = new JSONObject(s);
            headers.set(jsonObj.getString("crumbRequestField"), jsonObj.getString("crumb"));
        
        }
            return headers;
       
    }
}
