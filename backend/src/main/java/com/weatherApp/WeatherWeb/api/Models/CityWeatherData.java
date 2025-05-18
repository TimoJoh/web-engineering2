package com.weatherApp.WeatherWeb.api.Models;

public class CityWeatherData {
    private String city;
    private String temperature;
    private String condition;

    public CityWeatherData(){}

    public CityWeatherData(String city, String temperature, String condition) {
        this.city = city;
        this.temperature = temperature;
        this.condition = condition;
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
}
