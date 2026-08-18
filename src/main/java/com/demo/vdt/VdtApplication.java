package com.demo.vdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VdtApplication {

	public static void main(String[] args) {
		SpringApplication.run(VdtApplication.class, args);
	}

}