package ru.halcyon.telegrambot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.halcyon.telegrambot.support.GameNews;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecentNewsConsumer {
    private final UpdateProcessor updateProcessor;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "recent-news", groupId = "news")
    public void getRecentNews(String message) {
        List<GameNews> recentGamesNews;

        try {
            recentGamesNews = objectMapper.readValue(message, new TypeReference<List<GameNews>>() {});
            updateProcessor.sendRecentNews(recentGamesNews);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
