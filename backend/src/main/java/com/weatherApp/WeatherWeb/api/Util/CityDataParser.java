package com.weatherApp.WeatherWeb.api.Util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherApp.WeatherWeb.api.Models.CityWeatherData;

import java.io.InputStream;
import java.util.List;

public class CityDataParser {

    public static List<CityWeatherData> parseCityWeatherData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = CityDataParser.class
                    .getClassLoader()
                    .getResourceAsStream("response.json");

            return mapper.readValue(is, new TypeReference<List<CityWeatherData>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // leere Liste bei Fehler
        }
    }
}
