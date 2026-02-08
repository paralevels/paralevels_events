package com.paralevels.events.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

  @Bean
  public RestClient restClient() {
    return RestClient.builder()
        // Default headers for all outgoing requests
        .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)

        // Timeouts (important in event pipelines)
        .requestFactory(factory -> {
          factory.setConnectTimeout(Duration.ofSeconds(5));
          factory.setReadTimeout(Duration.ofSeconds(10));
        })

        .build();
  }
}
