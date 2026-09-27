package com.travel.ai_travel_planner.service;

import com.travel.ai_travel_planner.responsedto.GeocodingResponse;
import org.springframework.stereotype.Service;

@Service
public interface GeocodingService {

    public GeocodingResponse getLocation(String city);
}
