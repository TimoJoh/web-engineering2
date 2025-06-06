package com.weatherApp.WeatherWeb.api.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Represents the hourly weather response received from an external weather API.
 * This class contains information about the city and a list of hourly forecasts.
 */
public class HourlyWeatherResponse {

    private City city;
    private List<HourlyForcast> list;

    /**
     * Returns the city information.
     *
     * @return {@link City} object with name and timezone information
     */
    public City getCity() {
        return city;
    }

    /**
     * Sets the city information.
     *
     * @param city {@link City} object
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Returns the list of hourly weather forecasts.
     *
     * @return List of {@link HourlyForcast} objects
     */
    public List<HourlyForcast> getList() {
        return list;
    }

    /**
     * Sets the list of hourly weather forecasts.
     *
     * @param list List of {@link HourlyForcast} objects
     */
    public void setList(List<HourlyForcast> list) {
        this.list = list;
    }

    /**
     * Contains information about the city, including its name and timezone.
     */
    public static class City {
        private String name;
        private int timezone;

        /**
         * Returns the city's timezone (in seconds from UTC offset).
         *
         * @return Timezone offset in seconds
         */
        public int getTimezone() {
            return timezone;
        }

        /**
         * Sets the city's timezone.
         *
         * @param timezone Timezone offset in seconds
         */
        public void setTimezone(int timezone) {
            this.timezone = timezone;
        }

        /**
         * Returns the name of the city.
         *
         * @return City name
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name of the city.
         *
         * @param name City name
         */
        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * Represents a single hourly weather forecast.
     */
    public static class HourlyForcast {
        private long dt;
        private String formattedTime;
        private Main main;
        private List<Weather> weather;
        private Rain rain;

        /**
         * No-args constructor for Jackson deserialization.
         */
        public HourlyForcast() {}

        /**
         * Returns the Unix timestamp (seconds since 1970) of the forecast.
         *
         * @return Unix timestamp
         */
        public long getDt() {
            return dt;
        }

        /**
         * Sets the Unix timestamp (seconds since 1970) of the forecast.
         *
         * @param dt Unix timestamp
         */
        public void setDt(long dt) {
            this.dt = dt;
        }

        /**
         * Returns the {@link Main} object containing temperature information.
         *
         * @return {@link Main} object
         */
        public Main getMain() {
            return main;
        }

        /**
         * Sets the {@link Main} object with temperature information.
         *
         * @param main {@link Main} object
         */
        public void setMain(Main main) {
            this.main = main;
        }

        /**
         * Returns the list of weather information (e.g., "Clouds", "Rain").
         *
         * @return List of {@link Weather} objects
         */
        public List<Weather> getWeather() {
            return weather;
        }

        /**
         * Sets the list of weather information.
         *
         * @param weather List of {@link Weather} objects
         */
        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        /**
         * Returns the rain data.
         * If null, a new Rain object is returned to avoid NullPointerExceptions.
         *
         * @return {@link Rain} object
         */
        public Rain getRain() {
            return rain != null ? rain : new Rain();
        }

        /**
         * Sets the {@link Rain} object.
         *
         * @param rain {@link Rain} object
         */
        public void setRain(Rain rain) {
            this.rain = rain;
        }

        /**
         * Returns the formatted time (e.g., "14:00") of the forecast.
         *
         * @return Formatted time
         */
        public String getFormattedTime() {
            return formattedTime;
        }

        /**
         * Sets the formatted time.
         *
         * @param formattedTime Formatted time (e.g., "14:00")
         */
        public void setFormattedTime(String formattedTime) {
            this.formattedTime = formattedTime;
        }

        /**
         * Helper method to convert a Unix timestamp to a time string (HH:mm)
         * taking the timezone offset into account.
         *
         * @param unixTime Unix timestamp
         * @param timezoneOffsetInSeconds Timezone offset in seconds
         * @return Formatted time string (HH:mm)
         */
        private String convertUnixToTimeString(long unixTime, int timezoneOffsetInSeconds) {
            Instant instant = Instant.ofEpochSecond(unixTime);
            OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofTotalSeconds(timezoneOffsetInSeconds));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return offsetDateTime.format(formatter);
        }

        /**
         * Contains temperature information (e.g., current temperature).
         */
        public static class Main {
            private double temp;

            /**
             * Returns the temperature in degrees Celsius.
             *
             * @return Temperature
             */
            public double getTemp() {
                return temp;
            }

            /**
             * Sets the temperature in degrees Celsius.
             *
             * @param temp Temperature
             */
            public void setTemp(double temp) {
                this.temp = temp;
            }
        }

        /**
         * Contains information about the weather condition, e.g., main category and description.
         */
        public static class Weather {
            private String main;
            private String description;

            /**
             * Returns the main weather category (e.g., "Rain", "Clouds").
             *
             * @return Main category
             */
            public String getMain() {
                return main;
            }

            /**
             * Sets the main weather category.
             *
             * @param main Main category
             */
            public void setMain(String main) {
                this.main = main;
            }

            /**
             * Returns the detailed weather description.
             *
             * @return Description (e.g., "light rain")
             */
            public String getDescription() {
                return description;
            }

            /**
             * Sets the detailed weather description.
             *
             * @param description Description
             */
            public void setDescription(String description) {
                this.description = description;
            }
        }

        /**
         * Contains data about the rain volume as returned by the API.
         */
        public static class Rain {
            @JsonProperty("1h")
            private Double oneHour;

            /**
             * Returns the rain volume for the last hour.
             * If not set, returns 0.0.
             *
             * @return Rain volume in mm
             */
            public Double getOneHour() {
                return oneHour != null ? oneHour : 0.0;
            }

            /**
             * Sets the rain volume for the last hour.
             *
             * @param oneHour Rain volume in mm
             */
            public void setOneHour(Double oneHour) {
                this.oneHour = oneHour;
            }
        }
    }
}