package com.travel.ai_travel_planner.service;

import com.travel.ai_travel_planner.requestdto.TripPlanRequest;
import com.travel.ai_travel_planner.responsedto.TripPlanResponse;
import org.springframework.stereotype.Service;

@Service
public interface TripService {
    TripPlanResponse planTrip(TripPlanRequest request);
}
