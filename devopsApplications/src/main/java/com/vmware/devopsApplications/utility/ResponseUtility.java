package com.vmware.devopsApplications.utility;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseUtility {

    public Map<String, Object> getResponseJson(String status,String message, Object a) {
        Map<String, Object> responseBuilder = new HashMap<>();
        responseBuilder.put("status", status);
        responseBuilder.put("message",message);
        responseBuilder.put("data", a);
        return responseBuilder;
    }

    public Map<String, Object> getResponseJson(String status,String message) {
        Map<String, Object> responseBuilder = new HashMap<>();
        responseBuilder.put("status", status);
        responseBuilder.put("message",message);
        return responseBuilder;
    }

}
