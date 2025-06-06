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
 * Service class for communication with the OpenWeatherMap API.
 *
 * This class encapsulates the logic to retrieve weather data,
 * including current weather, hourly, and daily forecasts.
 *
 * Weather data is fetched via REST calls to the OpenWeatherMap API,
 * then parsed into POJOs {@link CityWeatherData}, {@link HourlyWeatherResponse} and {@link DailyWeatherResponse}.
 *
 * <p><b>Scientific basis:</b>
 * The processing of timestamps is based on UNIX time (epoch time), which is the standard for time measurement in IT systems (Mills et al., 2013).
 * JSON processing is implemented using the Jackson library, an industry standard for JSON in Java (Fowler, 2015).
 * REST communication uses the Spring Framework, which is widely used for robust web services (Walls, 2016).
 * </p>
 *
 * @see <a href="https://openweathermap.org/api">OpenWeatherMap API documentation</a>
 * @see <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#rest-client">Spring REST Client</a>
 * @see <a href="https://github.com/FasterXML/jackson">Jackson JSON Processor</a>
 * @see <a href="https://en.wikipedia.org/wiki/Unix_time">Unix Time (Wikipedia)</a>
 */
@Service
public class WeatherService {

    /**
     * API key for the OpenWeatherMap API.
     * Injected from the application properties file.
     */
    @Value("${openweather.api.key}")
    private String apiKey;

    /**
     * RestTemplate used to perform HTTP requests.
     */
    private final RestTemplate restTemplate;

    /**
     * Constructor for dependency injection of RestTemplate.
     *
     * @param restTemplate Spring Boot injects this component for HTTP calls.
     */
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches current weather data for a city from OpenWeatherMap.
     *
     * Example API request:
     * {@code https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric&lang=en}
     *
     * @param city Name of the city.
     * @return {@link CityWeatherData} containing current weather information.
     * @throws RuntimeException on errors fetching or parsing data.
     */
    public CityWeatherData getWeatherForCity(String city) {
        String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric&lang=en",
                city, apiKey
        );

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return parseWeatherResponse(response.getBody(), city);
        } else {
            throw new RuntimeException("Error fetching weather data");
        }
    }

    /**
     * Formats a UNIX timestamp to "HH:mm" time format (UTC).
     *
     * Example: UNIX timestamp 1672531200 becomes "08:00".
     *
     * @param unixSeconds UNIX timestamp in seconds since epoch.
     * @return formatted time as String.
     */
    private String formatUnixTime(long unixSeconds) {
        Instant instant = Instant.ofEpochSecond(unixSeconds);
        return DateTimeFormatter.ofPattern("HH:mm")
                .withZone(ZoneId.of("UTC"))
                .format(instant);
    }

    /**
     * Helper method to adjust time considering the timezone offset.
     *
     * @param dt timestamp.
     * @param offset timezone offset in seconds.
     * @return formatted time with offset.
     */
    private String applyOffset(long dt, long offset) {
        double time = dt + offset;
        return formatUnixTime((long) time);
    }

    /**
     * Parses the JSON response of current weather data and creates a {@link CityWeatherData} object.
     *
     * @param json JSON string of the API response.
     * @param city city name.
     * @return parsed {@link CityWeatherData}.
     * @throws RuntimeException on parsing errors.
     */
    private CityWeatherData parseWeatherResponse(String json, String city) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            long dt = root.path("dt").asLong();
            double lon = root.get("coord").path("lon").asDouble();
            double lat = root.get("coord").path("lat").asDouble();
            String temperature = root.get("main").path("temp").asText();
            String minTemperature = root.get("main").path("temp_min").asText();
            String maxTemperature = root.get("main").path("temp_max").asText();
            String feelsLike = root.path("main").path("feels_like").asText();
            int pressure = root.path("main").path("pressure").asInt();
            int humidity = root.path("main").path("humidity").asInt();
            long sunrise = root.path("sys").path("sunrise").asLong();
            long sunset = root.path("sys").path("sunset").asLong();
            double windSpeed = root.path("wind").path("speed").asDouble();
            int windDegree = root.path("wind").path("deg").asInt();
            String condition = root.get("weather").get(0).path("description").asText();
            int timezone = root.path("timezone").asInt();
            String formattedTime = applyOffset(dt, timezone);

            return new CityWeatherData(city, dt, formattedTime, lon, lat, temperature, minTemperature,
                    maxTemperature, condition, feelsLike, pressure, humidity, sunrise, sunset,
                    windSpeed, windDegree, timezone);

        } catch (Exception e) {
            throw new RuntimeException("Error parsing weather data", e);
        }
    }

    /**
     * Fetches hourly weather forecasts for a city.
     *
     * Example API request:
     * {@code https://pro.openweathermap.org/data/2.5/forecast/hourly?q={city}&appid={apiKey}&units=metric}
     *
     * @param cityName name of the city.
     * @return {@link HourlyWeatherResponse} with hourly forecasts.
     * @throws RuntimeException on errors fetching or parsing.
     */
    public HourlyWeatherResponse getHourlyWeatherForCity(String cityName) {
        String url = String.format(
                "https://pro.openweathermap.org/data/2.5/forecast/hourly?q=%s&appid=%s&units=metric",
                cityName, apiKey
        );

        ResponseEntity<HourlyWeatherResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                HourlyWeatherResponse.class
        );
        HourlyWeatherResponse weatherResponse = response.getBody();

        if (weatherResponse != null && weatherResponse.getCity() != null) {
            int timezoneOffset = weatherResponse.getCity().getTimezone();

            for (HourlyWeatherResponse.HourlyForcast forecast : weatherResponse.getList()) {
                long timestamp = forecast.getDt();
                String formatted = applyOffset(timestamp, timezoneOffset);
                forecast.setFormattedTime(formatted);
            }
        }
        return weatherResponse;
    }

    /**
     * Fetches daily weather forecast for a city (7 days).
     *
     * Example API request:
     * {@code https://api.openweathermap.org/data/2.5/forecast/daily?q={city}&cnt=7&appid={apiKey}&units=metric&lang=en}
     *
     * @param cityName name of the city.
     * @return {@link DailyWeatherResponse} with daily forecasts or null on errors.
     */
    public DailyWeatherResponse getDailyWeatherByCity(String cityName) {
        String url = String.format(
                "https://api.openweathermap.org/data/2.5/forecast/daily?q=%s&cnt=7&appid=%s&units=metric&lang=en",
                cityName, apiKey
        );

        try {
            ResponseEntity<DailyWeatherResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    DailyWeatherResponse.class
            );
            return response.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }
}
