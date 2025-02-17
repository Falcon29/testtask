package com.example.testtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class TesttaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesttaskApplication.class, args);
	}

}
