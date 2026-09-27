package com.travel.ai_travel_planner.serviceimpl;

import com.travel.ai_travel_planner.responsedto.*;
import com.travel.ai_travel_planner.requestdto.TripPlanRequest;
import com.travel.ai_travel_planner.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TripServiceImpl implements TripService {

    private final GeocodingService geocodingService;
    private final WeatherService weatherService;
    private final PlacesService placesService;
    private final RouteService routeService;
    private final AIService aiService;

    public TripServiceImpl(GeocodingService geocodingService, WeatherService weatherService,PlacesService placesService, RouteService routeService, AIService aiService) {
        this.geocodingService = geocodingService;
        this.weatherService = weatherService;
        this.placesService = placesService;
        this.routeService = routeService;
        this.aiService = aiService;
    }

    public TripPlanResponse planTrip(TripPlanRequest request) {

        // 1. Get destination coordinates
        GeocodingResponse geocodingResponse =
                geocodingService.getLocation(request.getDestination());

        // 2. Validate response
        if (geocodingResponse == null
                || geocodingResponse.getResults() == null
                || geocodingResponse.getResults().isEmpty()) {

            throw new RuntimeException(
                    "Destination not found: " + request.getDestination()
            );
        }

        // 3. Get first matching location
        GeocodingResponse.Location destinationLocation =
                geocodingResponse.getResults().get(0);

        log.info("location is : {}",destinationLocation.getLatitude());

        // 4. Get weather using coordinates
        WeatherResponse weatherResponse =
                weatherService.getWeather(
                        destinationLocation.getLatitude(),
                        destinationLocation.getLongitude()
                );

        // 5. Check weather
        System.out.println(
                "Temperature: "
                        + weatherResponse
                        .getCurrent()
                        .getTemperature_2m()
        );

        System.out.println(
                "Wind Speed: "
                        + weatherResponse
                        .getCurrent()
                        .getWind_speed_10m()
        );


        // 5. Get nearby places
        PlacesResponse placesResponse =
                placesService.getPlaces(
                        destinationLocation.getLatitude(),
                        destinationLocation.getLongitude()
                );

        log.info(
                "Places found: {}",
                placesResponse.getElements() != null
                        ? placesResponse.getElements().size()
                        : 0
        );

        // 1. Get Source coordinates
        GeocodingResponse geocodingResponseForSource =
                geocodingService.getLocation(request.getSource());

        // 2. Validate response
        if (geocodingResponseForSource == null
                || geocodingResponseForSource.getResults() == null
                || geocodingResponseForSource.getResults().isEmpty()) {

            throw new RuntimeException(
                    "Destination not found: " + request.getDestination()
            );
        }

        // 3. Get first matching location
        GeocodingResponse.Location sourceLocation =
                geocodingResponseForSource.getResults().get(0);

        RouteResponse routeResponse =
                routeService.getRoute(
                        sourceLocation.getLatitude(),
                        sourceLocation.getLongitude(),
                        destinationLocation.getLatitude(),
                        destinationLocation.getLongitude()
                );

        log.info("Route calculated successfully : {}",routeResponse.getRoutes());


        // =====================================================
        // 6. FINAL RESPONSE
        // =====================================================

        String itinerary =
                aiService.generateItinerary(
                        request,
                        weatherResponse,
                        placesResponse,
                        routeResponse
                );

        log.info("AI itinerary generated successfully");


        // =====================================================
        // 7. FINAL RESPONSE
        // =====================================================

        return new TripPlanResponse(
                "Trip planned successfully",
                itinerary
        );
    }
}