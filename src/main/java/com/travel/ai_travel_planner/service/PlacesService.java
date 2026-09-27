package com.travel.ai_travel_planner.service;

import com.travel.ai_travel_planner.responsedto.PlacesResponse;

public interface PlacesService {

    public PlacesResponse getPlaces(double latitude, double longitude);

}
