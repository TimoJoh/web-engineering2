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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000") // ggf. anpassen
public class AppController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==== HTML-Ansichten ====

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationsForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        System.out.println("User wurde angelegt");
        return "register_success";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }




    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/api/auth/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            httpRequest.getSession(true); // Session erzeugen

            return ResponseEntity.ok("Login erfolgreich");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Login fehlgeschlagen");
        }
    }
    @PostMapping("/api/auth/register")
    @ResponseBody
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
    @GetMapping("api/auth/me")
    @ResponseBody
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            // Z.B. allgemeine Infos oder Dummy zurückgeben
            Map<String, String> anonymousUser = new HashMap<>();
            anonymousUser.put("status", "anonymous");
            return ResponseEntity.ok(anonymousUser);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("firstName", userDetails.getFirstName());
        userInfo.put("email", userDetails.getUsername());
        userInfo.put("lastName", userDetails.getLastName());
        System.out.println("User: " + userInfo);
        return ResponseEntity.ok(userInfo);
    }
}
