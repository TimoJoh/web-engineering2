package com.weatherApp.WeatherWeb.api.config;

import com.weatherApp.WeatherWeb.api.Service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * Configuration class responsible for defining application-level security settings.
 * <p>
 * This includes CORS handling, endpoint authorization, form-based login/logout, CSRF configuration,
 * and integration of custom authentication providers.
 */
@Configuration
public class WebSecurityConfig {

    /**
     * Defines a custom {@link UserDetailsService} for loading user-specific data during authentication.
     *
     * @return UserDetailsService instance used by Spring Security
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    /**
     * Provides a password encoder using the BCrypt hashing function.
     *
     * @return PasswordEncoder used for password hashing and verification
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Injects the custom user details service implementation
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Configures the authentication provider using the DAO pattern,
     * setting up the custom UserDetailsService and password encoder.
     *
     * @return DaoAuthenticationProvider for user authentication
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Defines the main Spring Security filter chain. This controls security behavior such as:
     * - Endpoint access rules
     * - CORS support
     * - Session management
     * - Login/logout behavior
     * - CSRF disabling for stateless APIs
     *
     * @param http HttpSecurity context provided by Spring Security
     * @param corsConfigurationSource Cors configuration defined in {@link CorsConfig}
     * @return SecurityFilterChain configured security filter chain
     * @throws Exception if security configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                // Enables CORS configuration provided externally
                .cors(cors -> cors.configurationSource(corsConfigurationSource))

                // Registers the custom authentication provider
                .authenticationProvider(authenticationProvider())

                // Disables CSRF protection, which is typical for stateless APIs
                .csrf(csrf -> csrf.disable())

                // Configures authorization rules for HTTP endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/register",
                                "/process_register",
                                "/h2-console/**",
                                "/api/auth/login",
                                "/api/auth/register",
                                "/api/auth/me",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/api/cities",
                                "/api/cities/add",
                                "/api/cities/**",
                                "/api/cities/delete/**",
                                "/**" // NOTE: Permits all routes – consider removing this in production
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // Customizes form login handling for API clients
                .formLogin(form -> form
                        .loginProcessingUrl("/api/auth/login")
                        .successHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Invalid credentials\"}");
                        })
                        .permitAll()
                )

                // Configures logout endpoint for the API
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .permitAll()
                )

                // Disables frame options to allow embedded H2 database console
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))

                // Limits the number of concurrent sessions per user
                .sessionManagement(session -> session
                        .maximumSessions(1)
                );

        return http.build();
    }

    /**
     * Provides the authentication manager used by Spring Security for authentication operations.
     *
     * @param authConfig injected AuthenticationConfiguration
     * @return AuthenticationManager used across the application
     * @throws Exception in case of initialization failure
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
