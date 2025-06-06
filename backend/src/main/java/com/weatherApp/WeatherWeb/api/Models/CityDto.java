package com.weatherApp.WeatherWeb.api.Models;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) for the {@link City} entity used in communication
 * between client and server.
 *
 * This class includes only essential fields and is typically used
 * to send or receive city names via the API.
 */
@Schema(description = "DTO representing a city as sent to or received from the API.")
public class CityDto {

    /**
     * Unique identifier of the city (assigned by the server).
     */
    @Schema(description = "Unique ID of the city", example = "17", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    /**
     * Name of the city (e.g., 'Berlin'), provided by the user.
     */
    @Schema(description = "Name of the city", example = "Berlin", required = true)
    private String name;

    /**
     * Default constructor required for serialization.
     */
    public CityDto() {}

    /**
     * Constructor to initialize a city DTO with values.
     *
     * @param id   the unique ID of the city
     * @param name the name of the city
     */
    public CityDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
