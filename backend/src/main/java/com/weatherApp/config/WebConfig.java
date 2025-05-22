package com.weatherApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Erlaubt CORS für alle Endpunkte
                .allowedOrigins("http://localhost:3000") // React Development Server
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Erlaubte Methoden
                .allowedHeaders("*") // Erlaubt alle Header
                .allowCredentials(false); // Nur für öffentliche APIs, kein Session Sharing
    }
} //Erlaubt React-Frontend Anfragen ans Backend zu senden