package com.example.demo.service;

import com.example.demo.config.GeminiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Autowired
    private GeminiConfig geminiConfig;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateContent(String prompt) {

        String url = geminiConfig.getUrl() + "?key=" + geminiConfig.getKey();

        Map<String, Object> request = new HashMap<>();
        request.put("contents", List.of(
                Map.of("parts", List.of(
                        Map.of("text", prompt)))
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        // Extract response
        List<Map> candidates = (List<Map>) response.getBody().get("candidates");
        Map firstCandidate = (Map) candidates.get(0);
        Map content = (Map) firstCandidate.get("content");
        List<Map> parts = (List<Map>) content.get("parts");
        return parts.get(0).get("text").toString();
    }
}
