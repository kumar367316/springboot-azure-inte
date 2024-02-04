package com.springboot.azure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootAzureInteApplication {

	public static void main(String[] args) {
		System.out.println("New Deployment");
		SpringApplication.run(SpringbootAzureInteApplication.class, args);
	}

}
