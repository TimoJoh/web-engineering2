package com.weatherApp.WeatherWeb.api.Service;

import com.weatherApp.WeatherWeb.api.Models.CityWeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherApp.WeatherWeb.api.Models.HourlyWeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

    private String formatUnixTime(long unixSeconds) {
        Instant instant = Instant.ofEpochSecond(unixSeconds);
        return DateTimeFormatter.ofPattern("HH:mm")
                .withZone(ZoneId.of("Europe/Berlin")) // lokale Zeitzone
                .format(instant);
    }

    private CityWeatherData parseWeatherResponse(String json, String city) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            String temperature = root.get("main").path("temp").asText() + "°C";
            String fellsLike = root.path("main").path("feels_like").asText() + "°C";
            int pressure = root.path("main").path("pressure").asInt();
            int humidity = root.path("main").path("humidity").asInt();

            long sunriseRaw = root.path("sys").path("sunrise").asLong();
            long sunsetRaw = root.path("sys").path("sunset").asLong();

            String sunrise = formatUnixTime(sunriseRaw);
            String sunset = formatUnixTime(sunsetRaw);

            double windSpeed = root.path("wind").path("speed").asDouble();
            int windDegree = root.path("wind").path("deg").asInt();

            String condition = root.get("weather").get(0).path("description").asText();

            return new CityWeatherData(city, temperature, condition,
                    fellsLike, pressure, humidity,
                    sunrise, sunset, windSpeed, windDegree);

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Parsen der Wetterdaten", e);
        }
    }

    public HourlyWeatherResponse getHourlyWeatherForCity(String cityName) {
        String url = String.format("https://pro.openweathermap.org/data/2.5/forecast/hourly?q=%s&appid=%s&units=metric",
                cityName, apiKey);
            ResponseEntity<HourlyWeatherResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    HourlyWeatherResponse.class
            );

            return response.getBody();
    }
}