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

