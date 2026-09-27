package com.travel.ai_travel_planner.responsedto;

import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {

    private Current current;
    private Daily daily;

    @Data
    public static class Current {

        private Double temperature_2m;
        private Double wind_speed_10m;
        private Integer weather_code;
    }

    @Data
    public static class Daily {

        private List<String> time;
        private List<Double> temperature_2m_max;
        private List<Double> temperature_2m_min;
        private List<Integer> precipitation_probability_max;
        private List<Double> precipitation_sum;
    }
}
