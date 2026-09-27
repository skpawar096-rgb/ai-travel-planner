package com.travel.ai_travel_planner.controller;

import com.travel.ai_travel_planner.service.AIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/api/ai/test")
    public String testAI(
            @RequestParam String prompt) {

        return aiService.ask(prompt);
    }
}
