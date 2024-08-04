package com.softuni.DeliciousRecipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class DeliciousRecipesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliciousRecipesApplication.class, args);
	}

}
