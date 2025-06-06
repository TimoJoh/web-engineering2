package com.weatherApp.WeatherWeb.api.Models;

import jakarta.validation.constraints.Email;

/**
 * Represents a login request object containing a user's email address and password
 * for authentication.
 * <p>
 * This class is typically used in the context of a login process.
 */
public class LoginRequest {

    /**
     * The user's email address.
     */
    private String email;

    /**
     * The user's password.
     */
    private String password;

    /**
     * Returns the user's email address.
     *
     * @return The email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's password.
     *
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}