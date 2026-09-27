package com.travel.ai_travel_planner.serviceimpl;

import com.travel.ai_travel_planner.responsedto.PlacesResponse;
import com.travel.ai_travel_planner.service.PlacesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import java.net.http.HttpRequest;

@Slf4j
@Service
public class PlacesServiceImpl implements PlacesService {

    private final ObjectMapper objectMapper;

    private final HttpClient httpClient =
            HttpClient.newHttpClient();

    public PlacesServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public PlacesResponse getPlaces(
            double latitude,
            double longitude) {

        try {

            String query =
                    "[out:json];"
                            + "("
                            + "node[\"tourism\"](around:10000,"
                            + latitude + "," + longitude + ");"
                            + "way[\"tourism\"](around:10000,"
                            + latitude + "," + longitude + ");"
                            + ");"
                            + "out center;";

            String encodedQuery =
                    URLEncoder.encode(
                            query,
                            StandardCharsets.UTF_8
                    );

            String url =
                    "https://overpass-api.de/api/interpreter"
                            + "?data="
                            + encodedQuery;

            log.info("Calling Places API");

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
                        "Places API failed: "
                                + response.statusCode()
                );
            }

            return objectMapper.readValue(
                    response.body(),
                    PlacesResponse.class
            );

        } catch (Exception e) {

            log.error(
                    "Error while fetching places",
                    e
            );

            throw new RuntimeException(
                    "Unable to fetch places",
                    e
            );
        }
    }
}
