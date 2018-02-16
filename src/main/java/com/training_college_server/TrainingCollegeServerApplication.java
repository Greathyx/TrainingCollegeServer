package com.training_college_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class TrainingCollegeServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingCollegeServerApplication.class, args);
	}

}
