package com.weatherApp.WeatherWeb.api.Service;

import com.weatherApp.WeatherWeb.api.Models.CityWeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherApp.WeatherWeb.api.Models.DailyWeatherResponse;
import com.weatherApp.WeatherWeb.api.Models.HourlyWeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * The {@code WeatherService} class is a service layer that contains the business logic
 * to interact with the OpenWeatherMap API. It provides methods to fetch current weather data
 * and hourly weather forecasts for a given city.
 */
@Service
public class WeatherService {

    /**
     * API key for accessing the OpenWeatherMap service.
     * Injected via the application properties file.
     */
    @Value("${openweather.api.key}")
    private String apiKey;

    /**
     * The {@link RestTemplate} object used to make HTTP requests to the external API.
     */
    private final RestTemplate restTemplate;

    /**
     * Constructor for injecting the {@link RestTemplate} dependency.
     *
     * @param restTemplate the {@link RestTemplate} instance.
     */
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches the current weather for a given city from the OpenWeatherMap API.
     * 
     * Example API request: 
     * {@code https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric&lang=en}
     *
     * @param city the name of the city for which weather data is requested.
     * @return a {@link CityWeatherData} object containing the parsed weather data.
     * @throws RuntimeException if there is an error during the API call or data parsing.
     */
    public CityWeatherData getWeatherForCity(String city) {
        String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric&lang=en",
                city, apiKey
        );

        // Perform HTTP GET request to the API
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            // Parse the API response and return it as a CityWeatherData object
            return parseWeatherResponse(response.getBody(), city);
        } else {
            // Throw an exception if the API call fails
            throw new RuntimeException("Error fetching weather data");
        }
    }

    /**
     * Formats a given UNIX timestamp into a human-readable time string.
     *
     * Example: Converts UNIX timestamp {@code 1672531200} to a string like "08:00".
     *
     * @param unixSeconds the UNIX timestamp in seconds since the epoch.
     * @return a formatted time string in the "HH:mm" format.
     */
    private String formatUnixTime(long unixSeconds) {
        Instant instant = Instant.ofEpochSecond(unixSeconds);
        return DateTimeFormatter.ofPattern("HH:mm")
                .withZone(ZoneId.of("Europe/Berlin")) // Specifies the local time zone
                .format(instant);
    }

    /**
     * Parses the JSON response returned by the OpenWeatherMap current weather API.
     * Extracts relevant weather information and constructs a {@link CityWeatherData} object.
     *
     * @param json the raw JSON string returned by the API.
     * @param city the name of the city for which the data is retrieved.
     * @return a {@link CityWeatherData} object containing the parsed weather data.
     * @throws RuntimeException if there is an error during JSON parsing.
     */
    private CityWeatherData parseWeatherResponse(String json, String city) {
        try {
            // Parse the JSON response using Jackson ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            // Extract the relevant fields from the JSON response
            String temperature = root.get("main").path("temp").asText();
            String minTemperatur = root.get("main").path("temp_min").asText();
            String maxTemperatur = root.get("main").path("temp_max").asText();
            String feelsLike = root.path("main").path("feels_like").asText();
            int pressure = root.path("main").path("pressure").asInt();
            int humidity = root.path("main").path("humidity").asInt();

            long sunriseRaw = root.path("sys").path("sunrise").asLong();
            long sunsetRaw = root.path("sys").path("sunset").asLong();

            String sunrise = formatUnixTime(sunriseRaw);
            String sunset = formatUnixTime(sunsetRaw);

            double windSpeed = root.path("wind").path("speed").asDouble();
            int windDegree = root.path("wind").path("deg").asInt();

            String condition = root.get("weather").get(0).path("description").asText();

            // Return a new CityWeatherData object containing the extracted data
            return new CityWeatherData(city, temperature, minTemperatur,
                    maxTemperatur, condition, feelsLike,
                    pressure, humidity, sunrise,
                    sunset, windSpeed, windDegree);

        } catch (Exception e) {
            // Throw an exception if JSON parsing fails
            throw new RuntimeException("Error parsing weather data", e);
        }
    }

    /**
     * Fetches hourly weather forecasts for a given city from the OpenWeatherMap API.
     * 
     * Example API request:
     * {@code https://pro.openweathermap.org/data/2.5/forecast/hourly?q={city}&appid={apiKey}&units=metric}
     *
     * @param cityName the name of the city for which hourly forecast is requested.
     * @return an {@link HourlyWeatherResponse} object containing the hourly forecast data.
     * @throws RuntimeException if the API call or response parsing fails.
     */
    public HourlyWeatherResponse getHourlyWeatherForCity(String cityName) {
        String url = String.format(
                "https://pro.openweathermap.org/data/2.5/forecast/hourly?q=%s&appid=%s&units=metric",
                cityName, apiKey
        );

        // Perform HTTP GET request for hourly forecasts
        ResponseEntity<HourlyWeatherResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                HourlyWeatherResponse.class
        );

        // Return the parsed hourly weather response
        return response.getBody();
    }

    public DailyWeatherResponse getDailyWeatherByCity(String cityName) {
        String url = String.format("https://api.openweathermap.org/data/2.5/forecast/daily?q=%s&cnt=7&appid=%s&units=metric&lang=en",
        cityName, apiKey
        );

        try{
            ResponseEntity<DailyWeatherResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    DailyWeatherResponse.class
            );
            //Return the parsed daily weather response
            return response.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }
}