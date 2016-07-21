package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.service.RestConsumerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class RestConsumerServiceImpl implements RestConsumerService{

    @Override
    public String post(String url, String data) {
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url + data, null, String.class);
        return response.getStatusCode().toString();
    }

    @Override
    public String get(String url, String data) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForObject(url + data, null, String.class);
        return response.getStatusCode().toString();
    }
    
}
