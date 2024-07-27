package ru.halcyon.aggregatorservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.halcyon.aggregatorservice.model.GameNews;
import ru.halcyon.aggregatorservice.service.GameNewsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games/news")
@RequiredArgsConstructor
public class GameNewsController {
    private final GameNewsService gameNewsService;

    @GetMapping
    public ResponseEntity<List<GameNews>> getLastNews(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "5") int limit
    ) {
        List<GameNews> gameNews = gameNewsService.findLastNews(offset, limit);
        return ResponseEntity.ok(gameNews);
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<GameNews> getById(@PathVariable long newsId) {
        GameNews gameNews = gameNewsService.findById(newsId);
        return ResponseEntity.ok(gameNews);
    }

    @GetMapping("/search")
    public ResponseEntity<List<GameNews>> search(@RequestParam String title) {
        List<GameNews> gameNews = gameNewsService.searchNewsByTitle(title);
        return ResponseEntity.ok(gameNews);
    }
}
