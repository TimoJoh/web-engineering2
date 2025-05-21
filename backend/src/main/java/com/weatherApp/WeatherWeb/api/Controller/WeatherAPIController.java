package com.weatherApp.WeatherWeb.api.Controller;

import com.weatherApp.WeatherWeb.api.Models.DailyWeatherResponse;
import com.weatherApp.WeatherWeb.api.Models.HourlyWeatherResponse;
import com.weatherApp.WeatherWeb.api.Models.CityWeatherData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.weatherApp.WeatherWeb.api.Service.WeatherService;

/**
 * The {@code WeatherAPIController} class serves as a REST controller
 * for handling HTTP requests related to weather data. It provides
 * endpoints to retrieve current weather and hourly weather forecasts
 * for a specified city.
 */
@RestController
@RequestMapping("/api")
public class WeatherAPIController {

    /**
     * Dependency injection for the {@link WeatherService},
     * which contains the business logic to fetch and process weather data.
     */
    private final WeatherService weatherService;

    /**
     * Constructor for {@code WeatherAPIController}, initializing the required {@code WeatherService}.
     *
     * @param weatherService the {@link WeatherService} instance used to process weather data.
     */
    public WeatherAPIController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Endpoint to retrieve the current weather data for a specified city.
     * 
     * Example Request:
     * {@code GET /api/weather?city=Berlin}
     *
     * @param city the name of the city for which weather data is requested.
     * @return a {@link ResponseEntity} containing a {@link CityWeatherData} object
     *         if successful, or a {@code BAD_REQUEST} status if an error occurs.
     */
    @GetMapping("/weather")
    public ResponseEntity<CityWeatherData> getWeather(@RequestParam String city) {
        try {
            // Fetch the current weather data from the service
            CityWeatherData data = weatherService.getWeatherForCity(city);
            return ResponseEntity.ok(data); // Return the data with an HTTP 200 status
        } catch (Exception e) {
            // Return a BAD_REQUEST status in case of an exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Endpoint to retrieve the hourly weather forecast for a specified city.
     * 
     * Example Request:
     * {@code GET /api/hourly?city=Berlin}
     *
     * @param city the name of the city for which hourly weather data is requested.
     * @return a {@link ResponseEntity} containing an {@link HourlyWeatherResponse} object
     *         with forecast details for the next hours.
     */
    @GetMapping("/hourly")
    public ResponseEntity<HourlyWeatherResponse> getHourlyWeather(@RequestParam String city) {
        // Fetch hourly weather forecasts from the service
        HourlyWeatherResponse response = weatherService.getHourlyWeatherForCity(city);
        return ResponseEntity.ok(response); // Return the forecast with an HTTP 200 status
    }

    @GetMapping("/daily")
    public ResponseEntity<DailyWeatherResponse> getDailyWeather(@RequestParam String city) {
        DailyWeatherResponse response = weatherService.getDailyWeatherByCity(city);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(response);
    }
}