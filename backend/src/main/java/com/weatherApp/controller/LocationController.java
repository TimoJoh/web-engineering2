package com.weatherApp.WeatherWeb.controller;

import com.weatherApp.WeatherWeb.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getLocation(HttpServletRequest request) {
        String ip = locationService.getClientIp(request);
        Map<String, Object> location = locationService.getLocationFromIp(ip);
        return ResponseEntity.ok(location);
    }
}
