package com.travel.ai_travel_planner.responsedto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PlacesResponse {

    private List<Place> elements;

    @Data
    public static class Place {

        private Long id;
        private String type;

        private Double lat;
        private Double lon;

        private Center center;
        private Map<String, String> tags;
    }

    @Data
    public static class Center {

        private Double lat;
        private Double lon;
    }
}