package ru.halcyon.aggregatorservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.halcyon.aggregatorservice.support.GameNewsMessage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsConsumer {
    private final GameNewsService gameNewsService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "games-news", groupId = "news")
    public void getNews(String message) {
        List<GameNewsMessage> gameNewsMessage;

        try {
            gameNewsMessage = objectMapper.readValue(message, new TypeReference<List<GameNewsMessage>>() {});
            gameNewsService.processGameNews(gameNewsMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
