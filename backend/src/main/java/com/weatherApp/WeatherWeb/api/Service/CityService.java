package com.weatherApp.WeatherWeb.api.Service;

import com.weatherApp.WeatherWeb.api.Models.City;
import com.weatherApp.WeatherWeb.api.Models.User;
import com.weatherApp.WeatherWeb.api.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    // Alle Städte abrufen
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    // Eine Stadt speichern
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    // Eine Stadt anhand ihrer ID abrufen
    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(id);
    }

    // Eine Stadt löschen
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    // Städte eines Benutzers abrufen
    public List<City> getCitiesByUser(User user) {
        return cityRepository.findByUser(user);
    }

    public Optional<City> findByUserIDAndCityName(Long userID, String cityName) {
        return cityRepository.findByUserIdAndName(userID, cityName);
    }
}