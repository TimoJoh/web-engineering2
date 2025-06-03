package com.weatherApp.WeatherWeb.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuration class for setting up Cross-Origin Resource Sharing (CORS) policy.
 * <p>
 * This configuration enables controlled access to backend resources from different origins,
 * especially from frontend applications such as those running on a separate domain or port (e.g., React dev server).
 */
@Configuration
public class CorsConfig {

    /**
     * Defines and registers a CORS configuration bean to allow cross-origin HTTP requests.
     *
     * @return CorsConfigurationSource an object specifying the CORS policy.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Specifies the allowed origin(s) for cross-origin requests.
        // This should match the domain of your frontend application.
        config.setAllowedOrigins(List.of("http://localhost:3000"));

        // Defines which HTTP methods are permitted from cross-origin requests.
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Indicates which request headers are allowed in CORS requests.
        // Wildcard "*" allows all headers; "Authorization" is explicitly included for security tokens.
        config.setAllowedHeaders(List.of("*", "Authorization"));

        // Enables credentials such as cookies or HTTP authentication to be included in cross-origin requests.
        config.setAllowCredentials(true);

        // Binds the CORS configuration to all application endpoints using a URL pattern.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
