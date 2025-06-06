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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.HashMap;
import java.util.Map;

/**
 * AppController handles basic web routes and REST endpoints for user authentication,
 * such as login, registration, user info retrieval, and logout functionality.
 */
@Controller
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend requests from this origin
@Tag(name = "AppController", description = "Handles web views and basic authentication endpoints.")
public class AppController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    // ===== REST API: Authentication Endpoints =====

    /**
     * Authenticates a user based on their email and password,
     * and creates a session upon successful login.
     *
     * @param request      LoginRequest containing email and password
     * @param httpRequest  HttpServletRequest to manage session creation
     * @return ResponseEntity with login status
     */
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
            // Create an authentication token using provided credentials
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

            // Authenticate the user via AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Create an HTTP session
            httpRequest.getSession(true);

            return ResponseEntity.ok("Login erfolgreich");
        } catch (AuthenticationException ex) {
            // Authentication failed
            return ResponseEntity.status(401).body("Login fehlgeschlagen");
        }
    }

    /**
     * Registers a new user after checking for email uniqueness and encoding the password.
     *
     * @param user User object containing registration data
     * @return ResponseEntity with registration status
     */
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
            // Check if a user with the given email already exists
            if (userRepo.existsByEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Benutzer existiert bereits");
            }

            // Encode the user's password and save the user
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);

            return ResponseEntity.ok("Registrierung erfolgreich");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler bei der Registrierung");
        }
    }

    /**
     * Returns details of the currently authenticated user.
     * If the user is not authenticated, it returns anonymous status.
     *
     * @param authentication Injected Authentication object from Spring Security
     * @return ResponseEntity with user info or anonymous status
     */
    @GetMapping("/api/auth/me")
    @ResponseBody
    @Operation(summary = "Get current authenticated user info", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        // If not authenticated or not of expected type, return anonymous info
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            Map<String, String> anonymousUser = new HashMap<>();
            anonymousUser.put("status", "anonymous");
            return ResponseEntity.ok(anonymousUser);
        }

        // Extract user details from authentication principal
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("firstName", userDetails.getFirstName());
        userInfo.put("email", userDetails.getUsername());
        userInfo.put("lastName", userDetails.getLastName());

        return ResponseEntity.ok(userInfo);
    }

    /**
     * Logs out the current user by invalidating the session and clearing the security context.
     *
     * @param request HttpServletRequest to access and invalidate the session
     * @return ResponseEntity with logout status
     */
    @PostMapping("/api/auth/logout")
    @ResponseBody
    @Operation(summary = "Logout the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "500", description = "Logout failed")
    })
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            // Invalidate session if it exists
            request.getSession(false).invalidate();

            // Clear the security context
            SecurityContextHolder.clearContext();

            return ResponseEntity.ok("Logout erfolgreich");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Logout");
        }
    }
}
