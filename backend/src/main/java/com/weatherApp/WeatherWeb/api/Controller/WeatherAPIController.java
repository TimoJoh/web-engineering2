package com.weatherApp.WeatherWeb.api.Controller;

import com.weatherApp.WeatherWeb.api.Models.DailyWeatherResponse;
import com.weatherApp.WeatherWeb.api.Models.HourlyWeatherResponse;
import com.weatherApp.WeatherWeb.api.Models.CityWeatherData;
import com.weatherApp.WeatherWeb.api.Service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST-Controller zur Bereitstellung von Wetterdaten via API.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Weather API", description = "Stellt Wetterinformationen bereit: aktuell, stündlich, täglich")
public class WeatherAPIController {

    private final WeatherService weatherService;

    public WeatherAPIController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Gibt die aktuellen Wetterdaten für eine Stadt zurück.
     *
     * @param city Name der Stadt (z. B. "Berlin")
     * @return CityWeatherData mit aktuellen Wetterinformationen
     */
    @Operation(summary = "Aktuelles Wetter", description = "Liefert aktuelle Wetterdaten für die angegebene Stadt.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Erfolgreich abgerufen"),
            @ApiResponse(responseCode = "400", description = "Fehlerhafte Anfrage (z. B. ungültiger Stadtnamen)")
    })
    @GetMapping("/weather")
    public ResponseEntity<CityWeatherData> getWeather(@RequestParam String city) {
        try {
            CityWeatherData data = weatherService.getWeatherForCity(city);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Gibt eine stündliche Wettervorhersage für eine Stadt zurück.
     *
     * @param city Name der Stadt
     * @return HourlyWeatherResponse mit Wetterdaten für die nächsten Stunden
     */
    @Operation(summary = "Stündliche Wettervorhersage", description = "Gibt die Wettervorhersage für die nächsten Stunden aus.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Erfolgreich abgerufen")
    })
    @GetMapping("/hourly")
    public ResponseEntity<HourlyWeatherResponse> getHourlyWeather(@RequestParam String city) {
        HourlyWeatherResponse response = weatherService.getHourlyWeatherForCity(city);
        return ResponseEntity.ok(response);
    }

    /**
     * Gibt eine tägliche Wettervorhersage für die nächsten Tage zurück.
     *
     * @param city Name der Stadt
     * @return DailyWeatherResponse mit täglichen Wetterdaten
     */
    @Operation(summary = "Tägliche Wettervorhersage", description = "Liefert die Wettervorhersage für mehrere Tage für eine Stadt.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Erfolgreich abgerufen"),
            @ApiResponse(responseCode = "404", description = "Stadt nicht gefunden oder keine Wetterdaten verfügbar")
    })
    @GetMapping("/daily")
    public ResponseEntity<DailyWeatherResponse> getDailyWeather(@RequestParam String city) {
        DailyWeatherResponse response = weatherService.getDailyWeatherByCity(city);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(response);
    }
}
