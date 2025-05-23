package com.weatherApp.WeatherWeb.api.Models;

/**
 * The {@code CityWeatherData} class represents current weather information
 * for a specific city. It includes details such as temperature, weather
 * conditions, wind speed, humidity, and more.
 */
public class CityWeatherData {

    // The name of the city
    private String city;

    // The current temperature as a formatted string (e.g., "20°C")
    private String temperature;

    //The minimum temperatur for the day
    private String minTemperature;

    // The maximum temperatur of the day
    private String maxTemperature;

    // The current weather condition (e.g., "Clear", "Rainy")
    private String condition;

    // The "feels like" temperature as a formatted string
    private String feelsLike;

    // The atmospheric pressure in hPa
    private int pressure;

    // The current humidity level as a percentage
    private int humidity;

    // The time of sunrise, formatted as a string
    private String sunrise;

    // The time of sunset, formatted as a string
    private String sunset;

    // The wind speed in meters per second
    private double windSpeed;

    // The wind direction in degrees (0-360)
    private double windDegree;

    private String windDirection;


    /**
     * Default constructor for {@code CityWeatherData}.
     */
    public CityWeatherData() {
    }

    /**
     * Constructor for {@code CityWeatherData}, initializing all fields.
     *
     * @param city       the name of the city
     * @param temperature the current temperature
     * @param condition   the current weather condition
     * @param feelsLike   the "feels like" temperature
     * @param pressure    the atmospheric pressure
     * @param humidity    the humidity level
     * @param sunrise     the time of sunrise
     * @param sunset      the time of sunset
     * @param windSpeed   the wind speed
     * @param windDegree  the wind direction
     */
    public CityWeatherData(String city, String temperature, String minTemperature,
                           String maxTemperature, String condition,
                           String feelsLike, int pressure, int humidity,
                           String sunrise, String sunset, double windSpeed,
                           double windDegree) {
        this.city = city;
        this.temperature = temperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.condition = condition;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.windDirection = windDegreeToDirection(windDegree);
    }

    /**
     * Gets the name of the city.
     * @return the name of the city
     */
    public String getCity() {
        return city;
    }
    /**
     * Gets the wind direction.
     * @return the wind direction
     */
    public String getWindDirection() {
        return windDirection;
    }

    /**
     * Sets the wind direction.
     * @param windDirection
     */
    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    /**
     * Sets the name of the city.
     * @param city the city name to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the current weather condition.
     * @return the weather condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Sets the current weather condition.
     * @param condition the weather condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Gets the current temperature.
     * @return the temperature
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * Sets the current temperature.
     * @param temperature the temperature to set
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * Gets the wind direction in degrees.
     * @return the wind direction in degrees
     */
    public double getWindDegree() {
        return windDegree;
    }

    /**
     * Sets the wind direction in degrees.
     * @param windDegree the wind direction to set
     */
    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }

    /**
     * Gets the wind speed.
     * @return the wind speed
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * Sets the wind speed.
     * @param windSpeed the wind speed to set
     */
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * Gets the time of sunset.
     * @return the sunset time
     */
    public String getSunset() {
        return sunset;
    }

    /**
     * Sets the time of sunset.
     * @param sunset the sunset time to set
     */
    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    /**
     * Gets the time of sunrise.
     * @return the sunrise time
     */
    public String getSunrise() {
        return sunrise;
    }

    /**
     * Sets the time of sunrise.
     * @param sunrise the sunrise time to set
     */
    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    /**
     * Gets the humidity level.
     * @return the humidity level
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * Sets the humidity level.
     * @param humidity the humidity level to set
     */
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    /**
     * Gets the atmospheric pressure.
     * @return the pressure in hPa
     */
    public int getPressure() {
        return pressure;
    }

    /**
     * Sets the atmospheric pressure.
     * @param pressure the pressure to set
     */
    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    /**
     * Gets the "feels like" temperature.
     * @return the "feels like" temperature
     */
    public String getFeelsLike() {
        return feelsLike;
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

    /**
     * Sets the "feels like" temperature.
     * @param feelsLike the "feels like" temperature to set
     */
    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    /**
     * Converts a wind direction in degrees to a human-readable direction.
     * @param windDegree
     * @return the wind direction as a human-readable string (e.g., "N").
     */
    private String windDegreeToDirection(double windDegree) {
        String[] richtungen = {"N", "NO", "O", "SO", "S", "SW", "W", "NW"};
        int index = (int) ((windDegree + 22.5) / 45) % 8;
        return richtungen[index];
        }
    }
