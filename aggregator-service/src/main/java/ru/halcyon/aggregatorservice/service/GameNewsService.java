package ru.halcyon.aggregatorservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.halcyon.aggregatorservice.exception.GameNewsNotFoundException;
import ru.halcyon.aggregatorservice.model.GameNews;
import ru.halcyon.aggregatorservice.repository.GameNewsRepository;
import ru.halcyon.aggregatorservice.support.GameNewsMessage;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameNewsService {
    private final GameNewsRepository gameNewsRepository;
    private final RecentNewsProducer recentNewsProducer;
    private final SimpMessagingTemplate messagingTemplate;

    public List<GameNews> findLastNews(int offset, int limit) {
        return gameNewsRepository.findAll(
                PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "createdAt"))
        ).getContent();
    }

    public GameNews findById(long newsId) {
        return gameNewsRepository.findById(newsId)
                .orElseThrow(() -> new GameNewsNotFoundException("Game news with this id not found."));
    }

    public List<GameNews> searchNewsByTitle(String title) {
        return gameNewsRepository.findGameNewsByTitleContainingIgnoreCase(title);
    }

    public void processGameNews(List<GameNewsMessage> gameNews) {
        List<GameNews> newGameNews = new ArrayList<>();

        for (GameNewsMessage news: gameNews) {
            if (!gameNewsRepository.existsByTitle(news.getTitle())) {
                newGameNews.add(GameNews.builder()
                        .title(news.getTitle())
                        .content(news.getContent())
                        .source(news.getSource())
                        .createdAt(news.getCreatedAt())
                        .tags(news.getTags())
                        .build());
            }
        }

        gameNewsRepository.saveAll(newGameNews);
        messagingTemplate.convertAndSend("/topic/news", newGameNews);
        recentNewsProducer.sendRecentNews(newGameNews);
    }
}
