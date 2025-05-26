package com.weatherApp.WeatherWeb.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The {@code WeatherWebApplication} class is the entry point for the Spring Boot application.
 * It initializes and starts the web application, setting up the required components and configurations.
 */
@SpringBootApplication
public class WeatherWebApplication {

    /**
     * The main method that serves as the entry point for the application.
     * It launches the Spring Boot application context.
     *
     * @param args an array of {@link String} arguments passed to the application (e.g., command-line arguments).
     */
    public static void main(String[] args) {
        // Start the Spring Boot application
        SpringApplication.run(WeatherWebApplication.class, args);
    }

}