package com.weatherApp.WeatherWeb.api.Service;

import com.weatherApp.WeatherWeb.api.Models.City;
import com.weatherApp.WeatherWeb.api.Models.User;
import com.weatherApp.WeatherWeb.api.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link City} objects.
 * <p>
 * This class encapsulates the business logic for CRUD operations on cities
 * as well as specific queries, such as retrieving cities belonging to a particular user.
 */
@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    /**
     * Returns all stored cities.
     *
     * @return List of all {@link City} objects
     */
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    /**
     * Saves a new city or updates an existing one.
     *
     * @param city The {@link City} object to be saved
     * @return The saved {@link City} object with generated ID
     */
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    /**
     * Finds a city by its ID.
     *
     * @param id The ID of the city
     * @return An {@link Optional} containing the city if found
     */
    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(id);
    }

    /**
     * Deletes a city by its ID.
     *
     * @param id The ID of the city to delete
     */
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    /**
     * Returns all cities associated with a specific user.
     *
     * @param user The {@link User} object whose cities are requested
     * @return List of associated {@link City} objects
     */
    public List<City> getCitiesByUser(User user) {
        return cityRepository.findByUser(user);
    }

    /**
     * Finds a city by user ID and city name.
     *
     * @param userID   The ID of the user
     * @param cityName The name of the city
     * @return An {@link Optional} containing the city if found
     */
    public Optional<City> findByUserIDAndCityName(Long userID, String cityName) {
        return cityRepository.findByUserIdAndName(userID, cityName);
    }
}
