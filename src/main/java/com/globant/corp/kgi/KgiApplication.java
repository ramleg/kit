package com.globant.corp.kgi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KgiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KgiApplication.class, args);
	}
}
