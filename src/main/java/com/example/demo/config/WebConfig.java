package com.example.demo.config;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFlux
@Configuration
public class WebConfig implements WebFluxConfigurer {

    @Autowired
    private GeminiConfig geminiConfig;

    @Bean
    public Client initGeminiClient() {
        return Client.builder()
                .apiKey(geminiConfig.getKey())
                .build();
    }
}
