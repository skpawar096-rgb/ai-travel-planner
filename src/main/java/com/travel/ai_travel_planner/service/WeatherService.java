package com.travel.ai_travel_planner.service;

import com.travel.ai_travel_planner.responsedto.WeatherResponse;
import org.springframework.stereotype.Service;

@Service
public interface WeatherService {

    public WeatherResponse getWeather(double latitude, double longitude);
}
