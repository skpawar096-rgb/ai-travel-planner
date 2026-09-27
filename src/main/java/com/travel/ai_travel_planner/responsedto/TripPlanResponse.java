package com.travel.ai_travel_planner.responsedto;

import lombok.Data;

@Data
public class TripPlanResponse {

    private String message;
    private String source;
    private String destination;
    private String itinerary;
    private int days;
    private int people;
    private double budget;
    private double latitude;
    private double longitude;

    private WeatherResponse weather;
    private PlacesResponse placesResponse;
    private RouteResponse routeResponse;


    public TripPlanResponse(
            String message,
            String source,
            String destination,
            double latitude,
            double longitude,
            int days,
            int people,
            double budget,
            WeatherResponse weather,
            PlacesResponse placesResponse, RouteResponse routeResponse, String itinerary) {

        this.message = message;
        this.source = source;
        this.destination = destination;
        this.latitude = latitude;
        this.longitude = longitude;
        this.days = days;
        this.people = people;
        this.budget = budget;
        this.weather = weather;
        this.placesResponse=placesResponse;
        this.routeResponse = routeResponse;
        this.itinerary=itinerary;
    }

    public TripPlanResponse(String tripPlannedSuccessfully, String itinerary) {
        this.itinerary = itinerary;
    }
}