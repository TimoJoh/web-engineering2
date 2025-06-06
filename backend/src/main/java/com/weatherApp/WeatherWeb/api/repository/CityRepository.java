package com.weatherApp.WeatherWeb.api.repository;

import com.weatherApp.WeatherWeb.api.Models.City;
import com.weatherApp.WeatherWeb.api.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing {@link City} entities.
 * <p>
 * Provides CRUD operations as well as custom queries for cities
 * associated with specific users.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    /**
     * Finds all cities associated with a specific user.
     *
     * @param user The user for whom the cities are searched
     * @return List of cities linked to the user
     */
    List<City> findByUser(User user);

    /**
     * Searches for a specific city by user ID and city name.
     *
     * @param userId   The ID of the user
     * @param cityName The name of the city
     * @return Optional containing the city if found; otherwise empty
     */
    Optional<City> findByUserIdAndName(Long userId, String cityName);
}
