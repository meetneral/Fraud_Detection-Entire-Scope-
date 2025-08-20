package com.teemlaren.RTFD.ml;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Component
public class MlScoringClient {

    private final WebClient webClient;
    private final MlScorerProperties props;

    public MlScoringClient(MlScorerProperties props) {
        this.props = props;
        this.webClient = WebClient.builder().build();
    }

    public Double score(Map<String, Object> features) {
        try {
            // expects { "score": 0.0..1.0 }
            Map<String, Object> resp = webClient.post()
                    .uri(props.getUrl())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(features)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofMillis(800))
                    .onErrorResume(ex -> Mono.empty())
                    .block();

            if (resp != null && resp.get("score") != null) {
                return ((Number) resp.get("score")).doubleValue();
            }
        } catch (Exception ignored) {
        }
        return null; // scorer unavailable
    }

    public double getChallengeThreshold() {
        return props.getChallengeThreshold();
    }

    public double getDeclineThreshold() {
        return props.getDeclineThreshold();
    }
}

