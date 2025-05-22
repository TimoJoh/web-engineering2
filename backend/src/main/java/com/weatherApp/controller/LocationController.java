package com.weatherApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import com.weatherApp.service.LocationService;

@Controller
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/")
    public String home() {
        // Leite die Root-URL an "/location.html" oder "/location" weiter
        return "redirect:/location";
    }

    // 1. Zeigt die HTML-Seite mit dem JavaScript (Geolocation im Browser)
    @GetMapping("/page")
    public String locationPage() {
        return "redirect:/location.html"; //aus /static laden
    }

    // 2. Empfängt die vom Browser übermittelten Koordinaten
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> saveCoordinates(@RequestBody Map<String, Object> coords) {
        locationService.processCoordinates(coords);
        return ResponseEntity.ok("Koordinaten empfangen");
    }

}
