package com.weatherApp.WeatherWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.weatherApp")
public class WeatherWebApplication {
	public static void main(String[] args) {// Start the Spring Boot application
		SpringApplication.run(WeatherWebApplication.class, args);
	}

}