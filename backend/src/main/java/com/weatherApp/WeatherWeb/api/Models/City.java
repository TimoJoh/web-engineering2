package com.weatherApp.WeatherWeb.api.Models;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The {@code City} entity represents a city saved by a user.
 * Weather data can be retrieved for this city, and each city is associated with exactly one user.
 */
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "name"})
        }
)
@Schema(description = "Represents a city saved by a user.")
public class City {

    /**
     * Unique ID of the city (primary key, auto-generated).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the city", example = "42")
    private Long id;

    /**
     * Name of the city, e.g., "Berlin" or "New York".
     * The combination of user and city name must be unique.
     */
    @Schema(description = "Name of the city", example = "Berlin", required = true)
    private String name;

    /**
     * The user who owns this city entry.
     * Each city belongs to exactly one user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Schema(description = "User who owns this city (internally linked via user_id)", hidden = true)
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
