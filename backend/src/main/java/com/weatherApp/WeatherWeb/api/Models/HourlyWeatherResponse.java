package com.weatherApp.WeatherWeb.api.Models;

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

    /**
     * Represents the city information associated with this weather data.
     */
    private City city;

    /**
     * Represents a list of hourly forecasts for the specified city.
     */
    private List<HourlyForcast> list;

    /**
     * Gets the city details.
     * @return the {@link City} object containing information about the city.
     */
    public City getCity() {
        return city;
    }

    /**
     * Sets the city details.
     * @param city the {@link City} object to set.
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Gets the list of hourly forecasts.
     * @return a list of {@link HourlyForcast} objects.
     */
    public List<HourlyForcast> getList() {
        return list;
    }

    /**
     * Sets the list of hourly forecasts.
     * @param list a list of {@link HourlyForcast} objects to set.
     */
    public void setList(List<HourlyForcast> list) {
        this.list = list;
    }

    /**
     * Represents the basic information about a city.
     */
    public static class City {

        /**
         * The name of the city.
         */
        private String name;

        /**
         * Gets the name of the city.
         * @return the name of the city.
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name of the city.
         * @param name the name of the city to set.
         */
        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * Represents weather data for a specific hour.
     */
    public static class HourlyForcast {

        /**
         * The timestamp of the forecast in human-readable format (e.g., "HH:mm").
         */
        private String dt;

        /**
         * Information about the temperature at this hour.
         */
        private Main main;

        /**
         * A list of weather conditions (e.g., "Sunny", "Cloudy").
         */
        private List<Weather> weather;

        /**
         * Constructs an {@link HourlyForcast} object with its timestamp, temperature,
         * and weather conditions.
         *
         * @param dt      the timestamp (UNIX time) for this forecast.
         * @param main    the {@link Main} object containing temperature information.
         * @param weather a list of {@link Weather} objects containing weather conditions.
         */
        public HourlyForcast(long dt, Main main, List<Weather> weather) {
            this.dt = convertUnixToTimeString(dt);
            this.main = main;
            this.weather = weather;
        }

        /**
         * Gets the formatted timestamp of the forecast.
         * @return the formatted timestamp (e.g., "HH:mm").
         */
        public String getDt() {
            return dt;
        }

        /**
         * Sets the formatted timestamp of the forecast.
         * @param dt the formatted timestamp to set.
         */
        public void setDt(String dt) {
            this.dt = dt;
        }

        /**
         * Gets the temperature information.
         * @return the {@link Main} object containing the temperature.
         */
        public Main getMain() {
            return main;
        }

        /**
         * Sets the temperature information.
         * @param main the {@link Main} object to set.
         */
        public void setMain(Main main) {
            this.main = main;
        }

        /**
         * Gets the weather conditions for this forecast.
         * @return a list of {@link Weather} objects.
         */
        public List<Weather> getWeather() {
            return weather;
        }

        /**
         * Sets the weather conditions for this forecast.
         * @param weather a list of {@link Weather} objects to set.
         */
        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        /**
         * Represents the temperature details for the given hourly forecast.
         */
        public static class Main {

            /**
             * The temperature in degrees (e.g., Celsius).
             */
            private double temp;

            /**
             * Gets the temperature.
             * @return the temperature value.
             */
            public double getTemp() {
                return temp;
            }

            /**
             * Sets the temperature.
             * @param temp the temperature value to set.
             */
            public void setTemp(double temp) {
                this.temp = temp;
            }
        }

        /**
         * Represents specific weather conditions (e.g., "Clear Sky", "Rain").
         */
        public static class Weather {

            /**
             * A short description of the primary weather condition (e.g., "Rain").
             */
            private String main;

            /**
             * A detailed description of the weather condition.
             */
            private String description;

            /**
             * Gets the main weather condition.
             * @return a short description of the weather condition.
             */
            public String getMain() {
                return main;
            }

            /**
             * Sets the main weather condition.
             * @param main the short description of the weather condition to set.
             */
            public void setMain(String main) {
                this.main = main;
            }

            /**
             * Gets the detailed description of the weather condition.
             * @return the detailed description.
             */
            public String getDescription() {
                return description;
            }

            /**
             * Sets the detailed description of the weather condition.
             * @param description the detailed description to set.
             */
            public void setDescription(String description) {
                this.description = description;
            }
        }

        /**
         * Converts a UNIX timestamp to a human-readable time string (e.g., "HH:mm").
         *
         * @param unixTime the UNIX timestamp to convert (seconds since epoch).
         * @return the formatted time string.
         */
        public String convertUnixToTimeString(long unixTime) {
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneId.of("Europe/Berlin"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // Format example: "14:30"
            return dateTime.format(formatter);
        }
    }
}