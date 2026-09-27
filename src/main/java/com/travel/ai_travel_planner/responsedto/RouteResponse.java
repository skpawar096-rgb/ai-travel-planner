package com.travel.ai_travel_planner.responsedto;

import lombok.Data;

import java.util.List;

@Data
public class RouteResponse {

    private String code;
    private List<Route> routes;

    @Data
    public static class Route {

        private Double distance;
        private Double duration;
    }
}
