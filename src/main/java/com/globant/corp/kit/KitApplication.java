package com.globant.corp.kit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class KitApplication {

        private static final Logger log = LoggerFactory.getLogger(KitApplication.class);
    
	public static void main(String[] args) {
		SpringApplication.run(KitApplication.class, args);
	}
}
