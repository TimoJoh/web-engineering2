package com.weatherApp.WeatherWeb.api.config;

import com.weatherApp.WeatherWeb.api.Service.CustomUserDetailsService;
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

@Configuration
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/",
                        "/register",
                        "/process_register",
                        "/h2-console/**",
                        "/api/auth/login",
                        "/swagger-ui/index.html",
                        "/**"
                ).permitAll()
                .anyRequest().authenticated()
        )
                .formLogin(form -> form
                        .usernameParameter("email")
                        .defaultSuccessUrl("http://localhost:3000/weather", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll()
                        .logoutSuccessUrl("/")
                                .permitAll()
                        )

                .csrf(csrf -> csrf.disable()) // notwendig für H2-Konsole
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // neue Syntax
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
