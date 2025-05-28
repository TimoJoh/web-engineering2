
package com.weatherApp.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LocationService {

    // Methode zum Speichern oder Weiterverarbeiten der Koordinaten
    public void processCoordinates(Map<String, Object> coords) {
        System.out.println("Koordinaten im Service erhalten: Latitude=" + coords.get("latitude") +
                ", Longitude=" + coords.get("longitude"));

    }
}
/*
package com.weatherApp.service;

import com.weatherApp.models.WeatherResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class LocationService {

    // Variante 1: Direkter API-Call im Service selbst (aktiviert)
    public void processCoordinates(Map<String, Object> coords) {
        double latitude = (double) coords.get("latitude");
        double longitude = (double) coords.get("longitude");

        System.out.println("Koordinaten im Service erhalten: Latitude=" + latitude + ", Longitude=" + longitude);

        // Direkter API-Call an OpenWeatherMap
        getWeatherDirectly(latitude, longitude);
    }

    private void getWeatherDirectly(double latitude, double longitude) {
        String apiKey = "DEIN_API_KEY_HIER"; // <-- Ersetze durch deinen echten Key oder nutze @Value
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude +
                "&lon=" + longitude + "&appid=" + apiKey + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);

        response.getBody();
    }

    /*
    // Variante 2: Übergabe an WeatherService (für später)
    @Autowired
    private WeatherService weatherService;

    public WeatherResponse processCoordinates(Map<String, Object> coords) {
        double latitude = (double) coords.get("latitude");
        double longitude = (double) coords.get("longitude");

        System.out.println("Koordinaten im Service erhalten: Latitude=" + latitude + ", Longitude=" + longitude);

        // Später aktivieren:
        // return weatherService.getWeather(latitude, longitude);
    }
    */
//}
