package ru.halcyon.aggregatorservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.halcyon.aggregatorservice.model.GameNews;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecentNewsProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendRecentNews(List<GameNews> gameNews) {
        try {
            String message = objectMapper.writeValueAsString(gameNews);
            kafkaTemplate.send("recent-news", message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
