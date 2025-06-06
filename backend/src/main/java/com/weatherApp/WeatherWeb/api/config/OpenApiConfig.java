// src/main/java/com/weatherApp/WeatherWeb/api/config/OpenApiConfig.java
package com.weatherApp.WeatherWeb.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.*;

/**
 * Configuration class for customizing the OpenAPI (Swagger) documentation
 * of the WeatherWeb project.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Defines the OpenAPI specification for the WeatherWeb application.
     * This includes general API information and the setup of a security scheme
     * using JWT-based bearer authentication.
     *
     * @return an {@link OpenAPI} object used by Swagger to generate API documentation
     */
    @Bean
    public OpenAPI weatherApiOpenAPI() {
        return new OpenAPI()
                // Basic API metadata: title, version, and description
                .info(new Info()
                        .title("WeatherWeb API")
                        .version("1.0.0")
                        .description("API documentation for the WeatherWeb project"))

                // Define a security requirement named "bearerAuth"
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))

                // Register the security scheme with the components section
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))); // Specify the token format as JWT
    }
}
