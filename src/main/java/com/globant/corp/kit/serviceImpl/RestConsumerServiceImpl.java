package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.service.RestConsumerService;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class RestConsumerServiceImpl implements RestConsumerService{
    
    @Value("${kit.gata.url}")
    private String gataUrl;
    @Value("${kit.gata.loginUrl}")
    private String loginUrl;
    @Value("${kit.gata.approvalRequest}")
    private String appReq;
    @Value("${kit.gata.user}")
    private String gataUser;
    @Value("${kit.gata.passwd}")
    private String gataPasswd;
    
    
    @Override
    public ResponseEntity sendToGata(HashMap<String, String> body){
        
        HashMap<String, String> loginData = new HashMap<>();
        loginData.put("username", gataUser);
        loginData.put("password", gataPasswd);
                
        String token = getToken(gataUrl + loginUrl, loginData).get("session_token");
        System.out.println("Da Token: ---> " + token);
        try {
            return new RestTemplate().exchange(gataUrl + appReq, HttpMethod.POST, getHttpEntity(body, token), Object.class);
        } catch (IOException ex) {
            Logger.getLogger(RestConsumerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private HashMap<String, String> getToken(String url, HashMap<String, String> loginData) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> response = restTemplate.postForEntity(url, loginData, HashMap.class);
        return response.getBody();
    }
    
    private HttpEntity<HashMap> getHttpEntity(HashMap body, String token) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        headers.add("content-type", "application/json");
        
        return new HttpEntity<>(body, headers);

    }
    
    
    
    public String get(String url, String data) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForObject(url + data, null, String.class);
        return response.getStatusCode().toString();
    }
    
}
