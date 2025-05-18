package com.weatherApp.WeatherWeb.api.Models;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CityWeatherData {
    private String city;
    private String temperature;
    private String condition;
    private String feelsLike;
    private int pressure;
    private int humidity;
    private String sunrise;
    private String sunset;
    private double windSpeed;
    private double windDegree;

    public CityWeatherData(){}

    public CityWeatherData(String city, String temperature, String condition,
                           String feelsLike, int pressure, int humidity,
                           String sunrise, String sunset, double windSpeed,
                           double windDegree) {
        this.city = city;
        this.temperature = temperature;
        this.condition = condition;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;

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

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
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

}
