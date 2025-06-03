package com.weatherApp.WeatherWeb.api.Models;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Die {@code City}-Entität stellt eine vom Benutzer gespeicherte Stadt dar,
 * zu der Wetterdaten abgerufen werden können.
 * Jede Stadt gehört eindeutig zu einem Benutzer.
 */
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "name"})
        }
)
@Schema(description = "Repräsentiert eine vom Benutzer gespeicherte Stadt.")
public class City {

    /**
     * Eindeutige ID der Stadt (Primärschlüssel, automatisch generiert).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Eindeutige ID der Stadt", example = "42")
    private Long id;

    /**
     * Name der Stadt, z.B. "Berlin" oder "New York".
     * In Kombination mit dem Benutzer eindeutig.
     */
    @Schema(description = "Name der Stadt", example = "Berlin", required = true)
    private String name;

    /**
     * Benutzer, dem die Stadt zugeordnet ist.
     * Eine Stadt gehört genau zu einem Benutzer.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Schema(description = "Benutzer, dem diese Stadt gehört (intern verknüpft über user_id).", hidden = true)
    private User user;

    // Getter und Setter

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
