package com.weatherApp.WeatherWeb.api.security;

import java.util.Collection;

import com.weatherApp.WeatherWeb.api.Models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Implementation of {@link UserDetails} to provide custom user data
 * for Spring Security.
 * <p>
 * This class uses a {@link User} object for authentication
 * and authorization in the Spring Security context.
 */
public class CustomUserDetails implements UserDetails {

    private User user;

    /**
     * Creates a new {@code CustomUserDetails} object with a {@link User}.
     *
     * @param user The user object on which the security data is based
     */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * Returns the authorities granted to the user.
     * <p>
     * In this implementation, no specific roles are used,
     * so {@code null} is returned.
     *
     * @return {@code null} since no roles or authorities are defined
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Returns the user's password.
     *
     * @return The hashed password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username (in this case, the email address).
     *
     * @return The user's email address
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Returns {@code true} as accounts never expire in this application.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Returns the underlying {@link User} object.
     *
     * @return The user object
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user object.
     *
     * @param user The new user object
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns {@code true} as accounts are never locked by default.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Returns {@code true} as credentials never expire.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Returns {@code true} as all users are enabled by default.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Returns the full name of the user.
     *
     * @return User's first and last name concatenated as a single string
     */
    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    /**
     * Returns the user's first name.
     */
    public String getFirstName() {
        return user.getFirstName();
    }

    /**
     * Returns the user's last name.
     */
    public String getLastName() {
        return user.getLastName();
    }
}
