package ru.halcyon.collectorservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.halcyon.collectorservice.support.GameNews;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendNews(List<GameNews> gameNews) {
        try {
            String message = objectMapper.writeValueAsString(gameNews);
            kafkaTemplate.send("games-news", message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
