package com.medical.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig  {

    @Value("${llm.api.url}")
    private String llmApiUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.create(llmApiUrl);
    }
}
