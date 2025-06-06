package com.weatherApp.WeatherWeb.api.Service;

import com.weatherApp.WeatherWeb.api.Models.User;
import com.weatherApp.WeatherWeb.api.repository.UserRepository;
import com.weatherApp.WeatherWeb.api.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service for integrating user authentication with Spring Security.
 * <p>
 * Implements the {@link UserDetailsService} interface to load user information
 * by email address from the database. Used by Spring Security during the
 * authentication process.
 * </p>
 *
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @see com.weatherApp.WeatherWeb.api.security.CustomUserDetails
 * @see com.weatherApp.WeatherWeb.api.Models.User
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    /**
     * Loads a user by their email address.
     * <p>
     * This method is called by Spring Security to provide {@link UserDetails}
     * for the authentication process.
     * </p>
     *
     * @param email The user's email address (username)
     * @return A {@link UserDetails} object containing user data
     * @throws UsernameNotFoundException If no user is found with the given email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }
}
