package com.example.demo.controller;

import com.example.demo.config.GeminiConfig;
import com.example.demo.dto.GeminiRequestDto;
import com.example.demo.dto.GeminiResponseDto;
import com.example.demo.service.GeminiService;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/ai")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private Client geminiClient;

    @Value("${google.gemini.model}")
    private String geminiModel;

    // localhost:8787/demo/api/ai/generate
    @PostMapping("/generate")
    public Mono<String> generateText(@RequestBody Map<String, String> payload) {
        String prompt = payload.get("prompt");

        String response = geminiService.generateContent(prompt);

        return Mono.just(response);
    }

    // localhost:8787/demo/api/ai/generate/client
    @PostMapping("/generate/client")
    public Mono<GeminiResponseDto> generateTextByClient(@RequestBody GeminiRequestDto requestDto) {

        GenerateContentResponse response = geminiClient.models.generateContent(
                geminiModel,
                requestDto.getPrompt(),
                null);

        return Mono.justOrEmpty(new GeminiResponseDto(response.text()));
    }
}
