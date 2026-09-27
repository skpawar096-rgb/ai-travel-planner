package com.travel.ai_travel_planner.service;

import com.travel.ai_travel_planner.responsedto.RouteResponse;
import org.springframework.stereotype.Component;

@Component
public interface RouteService {
    RouteResponse getRoute(
            double sourceLatitude,
            double sourceLongitude,
            double destinationLatitude,
            double destinationLongitude
    );

}
