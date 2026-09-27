package com.travel.ai_travel_planner.serviceimpl;


import com.travel.ai_travel_planner.responsedto.RouteResponse;
import com.travel.ai_travel_planner.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Service
public class RouteServiceImpl implements RouteService {

    private final ObjectMapper objectMapper;

    private final HttpClient httpClient =
            HttpClient.newHttpClient();

    public RouteServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public RouteResponse getRoute(
            double sourceLatitude,
            double sourceLongitude,
            double destinationLatitude,
            double destinationLongitude) {

        try {

            String url =
                    "https://router.project-osrm.org/route/v1/driving/"
                            + sourceLongitude + ","
                            + sourceLatitude + ";"
                            + destinationLongitude + ","
                            + destinationLatitude
                            + "?overview=false";

            log.info("Calling OSRM Route API: {}", url);

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .GET()
                            .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() != 200) {

                throw new RuntimeException(
                        "Route API failed: "
                                + response.statusCode()
                );
            }

            return objectMapper.readValue(
                    response.body(),
                    RouteResponse.class
            );

        } catch (Exception e) {

            log.error(
                    "Error while fetching route",
                    e
            );

            throw new RuntimeException(
                    "Unable to fetch route",
                    e
            );
        }
    }
}
