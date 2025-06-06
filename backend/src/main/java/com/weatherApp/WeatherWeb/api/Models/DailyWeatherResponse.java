package com.weatherApp.WeatherWeb.api.Models;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

/**
 * The {@code DailyWeatherResponse} class represents the daily weather forecast
 * for a city over several days.
 */
public class DailyWeatherResponse {

    private List<Daily> list;
    private City city;

    /**
     * Returns the list of daily weather data.
     *
     * @return List of {@link Daily} objects
     */
    public List<Daily> getList() {
        return list;
    }

    /**
     * Sets the list of daily weather data.
     * Automatically sets the weekdays based on the city's timezone.
     *
     * @param list List of {@link Daily} objects
     */
    public void setList(List<Daily> list) {
        this.list = list;

        if (city != null && city.getTimezone() != 0) {
            for (Daily daily : list) {
                daily.setWeekdayFromUnix(city.getTimezone());
            }
        }
    }

    /**
     * Returns the city information.
     *
     * @return {@link City} object
     */
    public City getCity() {
        return city;
    }

    /**
     * Sets the city information.
     * Automatically updates the weekdays of existing Daily entries.
     *
     * @param city the {@link City} object with timezone information
     */
    public void setCity(City city) {
        this.city = city;

        if (list != null && city.getTimezone() != 0) {
            for (Daily daily : list) {
                daily.setWeekdayFromUnix(city.getTimezone());
            }
        }
    }

    /**
     * The {@code City} class contains information about the city's timezone.
     */
    public static class City {
        private int timezone;

        /**
         * Returns the city's timezone in seconds.
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
    }

    /**
     * The {@code Daily} class represents the weather report for a single day.
     */
    public static class Daily {

        private long dt;
        private Temp temp;
        private float rain;
        private List<Weather> weather;
        private String weekday;

        /**
         * Returns the amount of rain for the day in mm.
         *
         * @return Amount of rain in mm
         */
        public float getRain() {
            return rain;
        }

        /**
         * Sets the amount of rain for the day in mm.
         *
         * @param rain Amount of rain
         */
        public void setRain(float rain) {
            this.rain = rain;
        }

        /**
         * Returns the Unix timestamp for the day.
         *
         * @return Unix timestamp (seconds since 1970)
         */
        public long getDt() {
            return dt;
        }

        /**
         * Sets the Unix timestamp for the day.
         *
         * @param dt Unix timestamp
         */
        public void setDt(long dt) {
            this.dt = dt;
        }

        /**
         * Returns the temperature information for the day.
         *
         * @return {@link Temp} object
         */
        public Temp getTemp() {
            return temp;
        }

        /**
         * Sets the temperature information for the day.
         *
         * @param temp {@link Temp} object
         */
        public void setTemp(Temp temp) {
            this.temp = temp;
        }

        /**
         * Returns the weather description for the day.
         *
         * @return List of {@link Weather} objects
         */
        public List<Weather> getWeather() {
            return weather;
        }

        /**
         * Sets the weather description for the day.
         *
         * @param weather List of {@link Weather} objects
         */
        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        /**
         * Returns the name of the weekday (e.g., "Monday").
         *
         * @return The weekday
         */
        public String getWeekday() {
            return weekday;
        }

        /**
         * Sets the weekday manually (e.g., "Monday").
         *
         * @param weekday The name of the weekday
         */
        public void setWeekday(String weekday) {
            this.weekday = weekday;
        }

        /**
         * Calculates and sets the weekday based on the timestamp {@code dt}
         * and the given timezone.
         *
         * @param timezoneOffsetInSeconds The timezone offset in seconds
         */
        public void setWeekdayFromUnix(int timezoneOffsetInSeconds) {
            Instant instant = Instant.ofEpochSecond(dt);
            ZonedDateTime dateTime = instant.atOffset(ZoneOffset.ofTotalSeconds(timezoneOffsetInSeconds)).toZonedDateTime();
            this.weekday = dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        }

        /**
         * The {@code Temp} class contains minimum and maximum temperature data for the day.
         */
        public static class Temp {
            private double min;
            private double max;

            /**
             * Returns the minimum temperature of the day.
             *
             * @return Minimum temperature
             */
            public double getMin() {
                return min;
            }

            /**
             * Sets the minimum temperature of the day.
             *
             * @param min Minimum temperature
             */
            public void setMin(double min) {
                this.min = min;
            }

            /**
             * Returns the maximum temperature of the day.
             *
             * @return Maximum temperature
             */
            public double getMax() {
                return max;
            }

            /**
             * Sets the maximum temperature of the day.
             *
             * @param max Maximum temperature
             */
            public void setMax(double max) {
                this.max = max;
            }
        }

        /**
         * The {@code Weather} class describes the weather conditions (e.g., "Cloudy").
         */
        public static class Weather {
            private String description;

            /**
             * Returns the weather description.
             *
             * @return Description of the weather
             */
            public String getDescription() {
                return description;
            }

            /**
             * Sets the weather description.
             *
             * @param description Description of the weather
             */
            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}