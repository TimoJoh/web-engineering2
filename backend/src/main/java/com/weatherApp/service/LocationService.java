package com.weatherApp.WeatherWeb.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class LocationService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        return xfHeader != null ? xfHeader.split(",")[0] : request.getRemoteAddr();
    }

    public Map<String, Object> getLocationFromIp(String ip) {
        String url = "https://ipapi.co/" + ip + "/json/";
        return restTemplate.getForObject(url, Map.class);
    }
}
