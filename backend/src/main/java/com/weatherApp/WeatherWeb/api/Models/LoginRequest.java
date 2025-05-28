package com.weatherApp.WeatherWeb.api.Models;

import jakarta.validation.constraints.Email;

public class LoginRequest {
    private String email;
    private String password;

    // Getter + Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
