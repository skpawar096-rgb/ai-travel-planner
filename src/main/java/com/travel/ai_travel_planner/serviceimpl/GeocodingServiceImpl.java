package com.travel.ai_travel_planner.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.ObjectMapper;
import com.travel.ai_travel_planner.responsedto.GeocodingResponse;
import com.travel.ai_travel_planner.service.GeocodingService;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class GeocodingServiceImpl implements GeocodingService {

    private static final Logger log = LoggerFactory.getLogger(GeocodingServiceImpl.class);
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GeocodingServiceImpl(HttpClient httpClient,
                            ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public GeocodingResponse getLocation(String city) {

        String encodedCity =
                URLEncoder.encode(city, StandardCharsets.UTF_8);

        String url = "https://geocoding-api.open-meteo.com/v1/search"
                + "?name=" + encodedCity
                + "&count=1"
                + "&language=en"
                + "&format=json"
                + "&countryCode=IN";

        log.info("request for geocode api : {}",url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(java.time.Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "Geocoding API failed. Status: "
                                + response.statusCode()
                );
            }

            log.info("responsr for geocode api : {}",response.body());


            return objectMapper.readValue(
                    response.body(),
                    GeocodingResponse.class
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to fetch location for: " + city,
                    e
            );
        }
    }
}
