package com.travel.ai_travel_planner.serviceimpl;

import com.travel.ai_travel_planner.service.AIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import com.travel.ai_travel_planner.requestdto.TripPlanRequest;
import com.travel.ai_travel_planner.responsedto.PlacesResponse;
import com.travel.ai_travel_planner.responsedto.RouteResponse;
import com.travel.ai_travel_planner.responsedto.WeatherResponse;


@Slf4j
@Service
public class AIServiceImpl implements AIService {


    private final ChatClient chatClient;

    public AIServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public String generateItinerary(
            TripPlanRequest request,
            WeatherResponse weather,
            PlacesResponse places,
            RouteResponse route) {

        String prompt = """
                You are an expert travel planner.

                Create a practical and realistic travel itinerary.

                USER INFORMATION
                ----------------
                Source: %s
                Destination: %s
                Number of days: %d
                Number of people: %d
                Budget: %.2f

                WEATHER INFORMATION
                -------------------
                %s

                AVAILABLE PLACES
                ----------------
                %s

                ROUTE INFORMATION
                -----------------
                %s

                INSTRUCTIONS
                ------------
                1. Create a day-wise itinerary in detail.
                2. Consider the weather conditions.
                3. Use the available places provided above.
                4. Consider the route and travel duration.
                5. Keep the itinerary realistic.
                6. Consider the user's budget.
                7. Do not invent places that are not provided.
                8. Keep the response easy to understand.
                9. Give point wise and spaces bullet points 

                Format the response as:

                Day 1:
                Morning:
                Afternoon:
                Evening:

                Day 2:
                Morning:
                Afternoon:
                Evening:

                Continue for all requested days.
                """.formatted(
                request.getSource(),
                request.getDestination(),
                request.getDays(),
                request.getPeople(),
                request.getBudget(),
                weather,
                places,
                route
        );

        log.info("Sending travel planning request to Ollama");
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }

    @Override
    public String ask(String prompt) {
        return "";
    }
}