package com.weatherApp.WeatherWeb.api.repository;

import com.weatherApp.WeatherWeb.api.Models.City;
import com.weatherApp.WeatherWeb.api.Models.User;
import com.weatherApp.WeatherWeb.api.security.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByUser(User user);
   Optional<City> findByUserIdAndName(Long userId, String cityName);
}