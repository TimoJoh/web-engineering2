package com.weatherApp.WeatherWeb.api.Controller;

import com.weatherApp.WeatherWeb.api.Models.City;
import com.weatherApp.WeatherWeb.api.Models.User;
import com.weatherApp.WeatherWeb.api.repository.CityRepository;
import com.weatherApp.WeatherWeb.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    // DTO-Klasse für City (kann auch als eigene Datei ausgelagert werden)
    public static class CityDto {
        private Long id;
        private String name;

        public CityDto() {}

        public CityDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        // Getter und Setter
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    // Alle Städte des aktuell eingeloggten Users abrufen
    @GetMapping
    public ResponseEntity<List<CityDto>> getCities(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<CityDto> cities = cityRepository.findByUser(user)
                .stream()
                .map(city -> new CityDto(city.getId(), city.getName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(cities);
    }

    // Neue Stadt zum User hinzufügen
    @PostMapping
    public ResponseEntity<CityDto> addCity(@RequestBody CityDto cityDto, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        City city = new City();
        city.setName(cityDto.getName());
        city.setUser(user);

        city = cityRepository.save(city);

        CityDto responseDto = new CityDto(city.getId(), city.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // Stadt löschen (optional)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        City city = cityRepository.findById(id).orElse(null);
        if (city == null || !city.getUser().getId().equals(user.getId())) {
            // Stadt existiert nicht oder gehört nicht zum User
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        cityRepository.delete(city);
        return ResponseEntity.noContent().build();
    }
}
