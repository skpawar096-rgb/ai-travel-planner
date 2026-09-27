package com.travel.ai_travel_planner.serviceimpl;

import com.travel.ai_travel_planner.service.WeatherService;

import tools.jackson.databind.ObjectMapper;
import com.travel.ai_travel_planner.responsedto.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {

    private final ObjectMapper objectMapper;

    private final HttpClient httpClient =
            HttpClient.newHttpClient();

    public WeatherServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public WeatherResponse getWeather(
            double latitude,
            double longitude) {

        try {

            String url = String.format(
                    "https://api.open-meteo.com/v1/forecast" +
                            "?latitude=%s" +
                            "&longitude=%s" +
                            "&current=temperature_2m,wind_speed_10m,weather_code" +
                            "&daily=temperature_2m_max,temperature_2m_min," +
                            "precipitation_probability_max,precipitation_sum" +
                            "&timezone=auto",
                    latitude,
                    longitude
            );

            log.info("Calling Open-Meteo API: {}", url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            log.info("Weather API status: {}",
                    response.statusCode());

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "Weather API failed. Status: "
                                + response.statusCode()
                );
            }

            return objectMapper.readValue(
                    response.body(),
                    WeatherResponse.class
            );

        } catch (Exception e) {

            log.error("Error while fetching weather", e);

            throw new RuntimeException(
                    "Unable to fetch weather data", e
            );
        }
    }
}
