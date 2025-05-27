
package com.weatherApp.service;

import com.weatherApp.models.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    public WeatherResponse getWeather(double latitude, double longitude) {
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s", latitude, longitude, apiKey);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, WeatherResponse.class);
    }
}
