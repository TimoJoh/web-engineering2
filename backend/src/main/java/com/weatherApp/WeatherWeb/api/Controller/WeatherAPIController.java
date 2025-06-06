package com.weatherApp.WeatherWeb.api.Controller;

import com.weatherApp.WeatherWeb.api.Models.DailyWeatherResponse;
import com.weatherApp.WeatherWeb.api.Models.HourlyWeatherResponse;
import com.weatherApp.WeatherWeb.api.Models.CityWeatherData;
import com.weatherApp.WeatherWeb.api.Service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that provides endpoints for retrieving weather information
 * (current, hourly, and daily forecasts) for a specified city.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Weather API", description = "Provides weather information: current, hourly, and daily forecasts.")
public class WeatherAPIController {

    private final WeatherService weatherService;

    // Constructor-based dependency injection
    public WeatherAPIController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Returns the current weather data for a given city.
     *
     * @param city The name of the city (e.g., "Berlin")
     * @return A {@link CityWeatherData} object with current weather information
     */
    @Operation(summary = "Current weather", description = "Returns current weather data for the specified city.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request (e.g., invalid city name)")
    })
    @GetMapping("/weather")
    public ResponseEntity<CityWeatherData> getWeather(@RequestParam String city) {
        try {
            // Delegate to the service to fetch weather data
            CityWeatherData data = weatherService.getWeatherForCity(city);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            // Return 400 if the city is invalid or another error occurs
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Returns the hourly weather forecast for a given city.
     *
     * @param city The name of the city
     * @return A {@link HourlyWeatherResponse} with weather forecast for the next hours
     */
    @Operation(summary = "Hourly forecast", description = "Provides the weather forecast for the upcoming hours.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GetMapping("/hourly")
    public ResponseEntity<HourlyWeatherResponse> getHourlyWeather(@RequestParam String city) {
        // Fetch hourly forecast via weather service
        HourlyWeatherResponse response = weatherService.getHourlyWeatherForCity(city);
        return ResponseEntity.ok(response);
    }

    /**
     * Returns the daily weather forecast for the coming days for a given city.
     *
     * @param city The name of the city
     * @return A {@link DailyWeatherResponse} object containing daily forecasts
     */
    @Operation(summary = "Daily forecast", description = "Returns a multi-day weather forecast for a specified city.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "City not found or no weather data available")
    })
    @GetMapping("/daily")
    public ResponseEntity<DailyWeatherResponse> getDailyWeather(@RequestParam String city) {
        // Fetch daily forecast from the weather service
        DailyWeatherResponse response = weatherService.getDailyWeatherByCity(city);

        // Return 404 if no data is available
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(response);
    }
}
