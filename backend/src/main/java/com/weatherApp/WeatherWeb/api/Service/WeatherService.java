package com.weatherApp.WeatherWeb.api.Service;

import com.weatherApp.WeatherWeb.api.Models.CityWeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${openweather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CityWeatherData getWeatherForCity(String city) {
        String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric&lang=de",
                city, apiKey
        );

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    if (response.getStatusCode() == HttpStatus.OK) {
        //parse JSoN -> kommt noch
        return parseWeatherResponse(response.getBody(), city);
    } else {
        throw new RuntimeException("Fehler beim Aufrufen der Wetterdaten");
    }
    }

    private CityWeatherData parseWeatherResponse(String json, String city) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            String temperature = root.get("main").path("temp").asText() + "°C";
            String condition = root.get("weather").get(0).path("description").asText();

            return new CityWeatherData(city, temperature, condition);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Parsen der Wetterdaten", e);
        }
    }
}