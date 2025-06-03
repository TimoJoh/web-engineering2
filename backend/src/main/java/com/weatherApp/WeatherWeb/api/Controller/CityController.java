package com.weatherApp.WeatherWeb.api.Controller;

import com.weatherApp.WeatherWeb.api.Models.City;
import com.weatherApp.WeatherWeb.api.Models.CityDto;
import com.weatherApp.WeatherWeb.api.Service.CityService;
import com.weatherApp.WeatherWeb.api.Models.User;
import com.weatherApp.WeatherWeb.api.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "CityController", description = "Handles CRUD operations for cities related to the authenticated user")
public class CityController {

    @Autowired
    private CityService cityService;

    /**
     * Retrieves all cities associated with the currently authenticated user.
     *
     * @return List of cities as DTOs
     */
    @Operation(summary = "Get all cities", description = "Returns a list of cities for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cities retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
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

    /**
     * Adds a new city to the currently authenticated user's list.
     *
     * @param cityDto DTO with the name of the city to add
     * @return Created City DTO
     */
    @Operation(summary = "Add a new city", description = "Adds a new city to the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "City created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
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

        // Debug-Ausgabe zur Kontrolle
        System.out.println("Aktueller Benutzer: " + user.getId() + " / " + user.getEmail());

        city = cityService.saveCity(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CityDto(city.getId(), city.getName()));
    }

    /**
     * Deletes a city by its ID.
     *
     * @param id City ID
     * @return 204 No Content on success
     */
    @Operation(summary = "Delete city by ID", description = "Deletes a city using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "City deleted successfully")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a city by its name for the currently authenticated user.
     *
     * @param name City name
     * @return 204 No Content on success, or 404 Not Found
     */
    @Operation(summary = "Delete city by name", description = "Deletes a city by its name for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "City deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
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
