package com.weatherApp.WeatherWeb.api.Controller;

import com.weatherApp.WeatherWeb.api.Models.HourlyWeatherResponse;
import com.weatherApp.WeatherWeb.api.Models.CityWeatherData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.weatherApp.WeatherWeb.api.Service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WeatherAPIController {

    private final WeatherService weatherService;

    public WeatherAPIController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public ResponseEntity<CityWeatherData> getWeather(@RequestParam String city) {
        try {
            CityWeatherData data = weatherService.getWeatherForCity(city);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/hourly")
    public ResponseEntity<HourlyWeatherResponse> getHourlyWeather(@RequestParam String city) {
        HourlyWeatherResponse response = weatherService.getHourlyWeatherForCity(city);
        return ResponseEntity.ok(response);
    }

}