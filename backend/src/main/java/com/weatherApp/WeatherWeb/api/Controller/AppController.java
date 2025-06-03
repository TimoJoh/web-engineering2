package com.weatherApp.WeatherWeb.api.Controller;

import com.weatherApp.WeatherWeb.api.Models.LoginRequest;
import com.weatherApp.WeatherWeb.api.Models.User;
import com.weatherApp.WeatherWeb.api.repository.UserRepository;
import com.weatherApp.WeatherWeb.api.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "AppController", description = "Handles web views and basic authentication endpoints.")
public class AppController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ===== REST API: Auth endpoints =====

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/api/auth/login")
    @ResponseBody
    @Operation(summary = "Authenticate user and create session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            httpRequest.getSession(true); // Create session

            return ResponseEntity.ok("Login erfolgreich");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Login fehlgeschlagen");
        }
    }

    @PostMapping("/api/auth/register")
    @ResponseBody
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful"),
            @ApiResponse(responseCode = "400", description = "User already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (userRepo.existsByEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Benutzer existiert bereits");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return ResponseEntity.ok("Registrierung erfolgreich");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler bei der Registrierung");
        }
    }

    @GetMapping("/api/auth/me")
    @ResponseBody
    @Operation(summary = "Get current authenticated user info", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            Map<String, String> anonymousUser = new HashMap<>();
            anonymousUser.put("status", "anonymous");
            return ResponseEntity.ok(anonymousUser);
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("firstName", userDetails.getFirstName());
        userInfo.put("email", userDetails.getUsername());
        userInfo.put("lastName", userDetails.getLastName());
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/api/auth/logout")
    @ResponseBody
    @Operation(summary = "Logout the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "500", description = "Logout failed")
    })
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            request.getSession(false).invalidate(); // Invalidate session if exists
            SecurityContextHolder.clearContext();   // Clear authentication context
            return ResponseEntity.ok("Logout erfolgreich");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Logout");
        }
    }
}
