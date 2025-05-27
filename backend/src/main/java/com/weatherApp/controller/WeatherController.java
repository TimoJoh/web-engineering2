package com.weatherApp.controller;

import com.weatherApp.service.WeatherService;
import com.weatherApp.models.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {

            private final WeatherService weatherService;

            @Autowired
            public WeatherController(WeatherService weatherService) {
                this.weatherService = weatherService;
            }

            @GetMapping("/current")
            public WeatherResponse getCurrentWeather(@RequestParam double latitude, @RequestParam double longitude) {
                return weatherService.getWeather(latitude, longitude);
            }
}
