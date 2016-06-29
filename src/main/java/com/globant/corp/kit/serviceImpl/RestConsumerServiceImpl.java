package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.service.RestConsumerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
    String gataUrl;
    
    @Override
    public String postToGata(String data) {
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(gataUrl + data, null, String.class);
        HttpStatus status = response.getStatusCode();
        //String body = response.getBody(); to get the body, in case we needed.
        return status.toString();
    }
    
}
