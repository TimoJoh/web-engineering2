package com.weatherApp.WeatherWeb.api.Models;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class DailyWeatherResponse {

    private List<Daily> list;
    private City city;

    public List<Daily> getList() {
        return list;
    }

    public void setList(List<Daily> list) {
        this.list = list;

        // Setze für jeden Daily-Eintrag den Wochentag, sobald Liste gesetzt wird
        if (city != null && city.getTimezone() != 0) {
            for (Daily daily : list) {
                daily.setWeekdayFromUnix(city.getTimezone());
            }
        }
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;

        // Auch beim Setzen der City sicherstellen, dass Wochentage korrekt gesetzt werden
        if (list != null && city.getTimezone() != 0) {
            for (Daily daily : list) {
                daily.setWeekdayFromUnix(city.getTimezone());
            }
        }
    }

    public static class City {
        private int timezone;

        public int getTimezone() {
            return timezone;
        }

        public void setTimezone(int timezone) {
            this.timezone = timezone;
        }
    }

    public static class Daily {

        private long dt;
        private Temp temp;
        private float rain;
        private List<Weather> weather;
        private String weekday;  // → Automatisch gesetzter Wochentag

        public float getRain() {
            return rain;
        }

        public void setRain(float rain) {
            this.rain = rain;
        }

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }

        public Temp getTemp() {
            return temp;
        }

        public void setTemp(Temp temp) {
            this.temp = temp;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        public String getWeekday() {
            return weekday;
        }

        public void setWeekday(String weekday) {
            this.weekday = weekday;
        }

        public void setWeekdayFromUnix(int timezoneOffsetInSeconds) {
            Instant instant = Instant.ofEpochSecond(dt);
            ZonedDateTime dateTime = instant.atOffset(ZoneOffset.ofTotalSeconds(timezoneOffsetInSeconds)).toZonedDateTime();
            this.weekday = dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        }

        public static class Temp {
            private double min;
            private double max;

            public double getMin() {
                return min;
            }

            public void setMin(double min) {
                this.min = min;
            }

            public double getMax() {
                return max;
            }

            public void setMax(double max) {
                this.max = max;
            }
        }

        public static class Weather {
            private String description;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
