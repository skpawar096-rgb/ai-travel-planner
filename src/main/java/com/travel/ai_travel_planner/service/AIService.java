package com.travel.ai_travel_planner.service;

import com.travel.ai_travel_planner.requestdto.TripPlanRequest;
import com.travel.ai_travel_planner.responsedto.PlacesResponse;
import com.travel.ai_travel_planner.responsedto.RouteResponse;
import com.travel.ai_travel_planner.responsedto.WeatherResponse;

public interface AIService {

    public String ask(String prompt);
    public String generateItinerary(
            TripPlanRequest request,
            WeatherResponse weather,
            PlacesResponse places,
            RouteResponse route);
}
