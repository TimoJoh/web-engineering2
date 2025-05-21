package com.weatherApp.WeatherWeb.api.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Represents the hourly weather response obtained from an external weather API.
 * This class contains information about the city and a list of hourly forecasts.
 */
public class HourlyWeatherResponse {

    private City city;
    private List<HourlyForcast> list;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<HourlyForcast> getList() {
        return list;
    }

    public void setList(List<HourlyForcast> list) {
        this.list = list;
    }

    public static class City {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class HourlyForcast {
        private String dt;
        private Main main;
        private List<Weather> weather;
        private Rain rain;

        // No-Args-Konstruktor für Jackson
        public HourlyForcast() {}

        public String getDt() {
            return dt;
        }

        @JsonProperty("dt")
        public void setDt(long unixTime) {
            this.dt = convertUnixToTimeString(unixTime);
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        public Rain getRain() {
            return rain != null ? rain : new Rain();
        }


        public void setRain(Rain rain) {
            this.rain = rain;
        }

        public static class Main {
            private double temp;

            public double getTemp() {
                return temp;
            }

            public void setTemp(double temp) {
                this.temp = temp;
            }
        }

        public static class Weather {
            private String main;
            private String description;

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }

        public static class Rain {
            @JsonProperty("1h")
            private Double oneHour;

            public Double getOneHour() {
                return oneHour != null ? oneHour : 0.0;
            }

            public void setOneHour(Double oneHour) {
                this.oneHour = oneHour;
            }
        }

        private String convertUnixToTimeString(long unixTime) {
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneId.of("Europe/Berlin"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return dateTime.format(formatter);
        }
    }
}
