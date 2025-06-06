package com.weatherApp.WeatherWeb.api.repository;

import com.weatherApp.WeatherWeb.api.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing {@link User} entities.
 * <p>
 * Provides standard CRUD functionality as well as custom
 * methods for searching by email addresses.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     *
     * @param email The user's email address
     * @return The found user or {@code null} if no user was found
     */
    User findByEmail(String email);

    /**
     * Checks if a user exists with the given email address.
     *
     * @param email The email address to check for existence
     * @return {@code true} if a user with this email exists; otherwise {@code false}
     */
    boolean existsByEmail(String email);
}
