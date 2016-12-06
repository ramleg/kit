package com.globant.corp.kit.serviceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.globant.corp.kit.service.RestServiceConsumer;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class RestServiceConsumerImpl implements RestServiceConsumer{
    
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
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public ResponseEntity postToGata(HashMap<String, String> body){
        
        HashMap<String, String> loginData = new HashMap<>();
        loginData.put("username", gataUser);
        loginData.put("password", gataPasswd);
                
        String token = getToken(gataUrl + loginUrl, loginData).get("session_token");
        try {
            RestTemplate rt = new RestTemplate();
            return rt.exchange(gataUrl + appReq, HttpMethod.POST, getHttpEntity(body, token), Object.class);
            
        } catch (IOException ex) {
            logger.error("ERROR: Post To GATA 'RestTemplate.exchange()'");
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
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", token);
        
        HttpEntity<HashMap> entity = new HttpEntity<>(body, headers);
        
        return new HttpEntity<>(body, headers);
    }
    private String get(String url, String data) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForObject(url + data, null, String.class);
        return response.getStatusCode().toString();
    }
    
}


