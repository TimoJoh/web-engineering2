package com.weatherApp.WeatherWeb.api.Controller;

import com.weatherApp.WeatherWeb.api.Models.City;
import com.weatherApp.WeatherWeb.api.Models.CityDto;
import com.weatherApp.WeatherWeb.api.Service.CityService;
import com.weatherApp.WeatherWeb.api.Models.User;
import com.weatherApp.WeatherWeb.api.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    // Alle Städte des aktuell eingeloggten Users abrufen
    @GetMapping
    public ResponseEntity<List<CityDto>> getCities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        List<City> cities = cityService.getCitiesByUser(user);
        List<CityDto> cityDtos = cities.stream()
                .map(city -> new CityDto(city.getId(), city.getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(cityDtos);
    }

    // Neue Stadt zum User hinzufügen
    @PostMapping
    public ResponseEntity<CityDto> addCity(@RequestBody CityDto cityDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        City city = new City();
        city.setName(cityDto.getName());
        city.setUser(user);

        //Degug

        System.out.println("Aktueller Benutzer: " + user.getId() + " / " + user.getEmail());
        city = cityService.saveCity(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CityDto(city.getId(), city.getName()));
    }

    // Stadt löschen (optional)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCity(@RequestParam String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        Optional<City> cityOptional = cityService.findByUserIDAndCityName(user.getId(), name);
        if (cityOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stadt nicht gefunden");
        }

        cityService.deleteCity(cityOptional.get().getId());
        return ResponseEntity.noContent().build();
    }


}