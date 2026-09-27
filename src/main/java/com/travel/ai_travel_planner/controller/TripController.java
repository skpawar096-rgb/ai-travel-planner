package com.travel.ai_travel_planner.controller;

import com.travel.ai_travel_planner.requestdto.TripPlanRequest;
import com.travel.ai_travel_planner.responsedto.TripPlanResponse;
import com.travel.ai_travel_planner.service.TripService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/plan")
    public TripPlanResponse planTrip(@Valid @RequestBody TripPlanRequest request) {

        return tripService.planTrip(request);
    }
}
