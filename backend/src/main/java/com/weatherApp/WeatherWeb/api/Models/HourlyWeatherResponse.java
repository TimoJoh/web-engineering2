package com.weatherApp.WeatherWeb.api.Models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

        public HourlyForcast(long dt, Main main, List<Weather> weather) {
            this.dt = convertUnixToTimeString(dt);
            this.main = main;
            this.weather = weather;
        }

        public String getDt() {
            return dt;
        }

        public void setDt(String dt) {
            this.dt = dt;
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

        public String convertUnixToTimeString(long unixTime) {
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneId.of("Europe/Berlin"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // z. B. nur Uhrzeit
            return dateTime.format(formatter);
        }
    }
}
