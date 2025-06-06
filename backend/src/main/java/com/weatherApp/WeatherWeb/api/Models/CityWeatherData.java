package com.weatherApp.WeatherWeb.api.Models;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * The {@code CityWeatherData} class represents current weather information
 * for a specific city. It contains details such as temperature, weather conditions,
 * wind speed, humidity, and more.
 */
public class CityWeatherData {

    // Name of the city
    private String city;

    // Unix timestamp (e.g., for current time)
    private double dt;

    // Formatted date/time (e.g., "14:35")
    private String formattedTime;

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    // Current temperature as string (e.g., "20")
    private String temperature;

    // Minimum daily temperature
    private String minTemperature;

    // Maximum daily temperature
    private String maxTemperature;

    // Current weather condition (e.g., "Clear", "Rainy")
    private String condition;

    // "Feels like" temperature
    private String feelsLike;

    // Air pressure in hPa
    private int pressure;

    // Humidity in percent
    private int humidity;

    // Sunrise time (formatted)
    private String sunrise;

    // Sunset time (formatted)
    private String sunset;

    // Wind speed in m/s
    private double windSpeed;

    // Wind direction in degrees (0–360)
    private double windDegree;

    // Wind direction as cardinal direction (e.g., "NE")
    private String windDirection;

    // Timezone offset in seconds
    private int timezone;

    // Longitude
    private double lon;

    // Latitude
    private double lat;

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    /**
     * Default constructor for {@code CityWeatherData}.
     */
    public CityWeatherData() {}

    /**
     * Constructor with parameters to initialize all fields.
     */
    public CityWeatherData(String city, double dt, String formattedTime, double lon, double lat, String temperature,
                           String minTemperature, String maxTemperature, String condition,
                           String feelsLike, int pressure, int humidity,
                           long sunrise, long sunset, double windSpeed,
                           double windDegree, int timezone) {
        this.city = city;
        this.dt = dt;
        this.formattedTime = formattedTime;
        this.lon = lon;
        this.lat = lat;
        this.temperature = cutDigits(temperature);
        this.minTemperature = cutDigits(minTemperature);
        this.maxTemperature = cutDigits(maxTemperature);
        this.condition = condition;
        this.feelsLike = cutDigits(feelsLike);
        this.pressure = pressure;
        this.humidity = humidity;
        this.sunrise = convertUnixToTimeString(sunrise, timezone);
        this.sunset = convertUnixToTimeString(sunset, timezone);
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.windDirection = windDegreeToDirection(windDegree);
        this.timezone = timezone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = convertUnixToTimeString(sunset, timezone);
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = convertUnixToTimeString(sunrise, timezone);
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    /**
     * Converts wind direction in degrees (0–360°) to a cardinal direction.
     */
    private String windDegreeToDirection(double windDegree) {
        String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        int index = (int) ((windDegree + 22.5) / 45) % 8;
        return directions[index];
    }

    /**
     * Rounds a temperature string (e.g., "23.5") to whole number.
     */
    private String cutDigits(String number) {
        Double num = Double.valueOf(number);
        return String.format("%.0f", num);
    }

    /**
     * Converts a Unix timestamp and timezone offset to a time string
     * in format HH:mm (e.g., "06:45").
     */
    private String convertUnixToTimeString(long unixTime, int timezoneOffsetInSeconds) {
        Instant instant = Instant.ofEpochSecond(unixTime);
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofTotalSeconds(timezoneOffsetInSeconds));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return offsetDateTime.format(formatter);
    }
}
