package ru.halcyon.telegrambot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.halcyon.telegrambot.support.GameNews;

import java.util.List;

@FeignClient(
        name = "aggregator-service",
        url = "http://aggregator-service:8080/api/v1/games/news"
)
public interface GameNewsClient {
    @GetMapping
    List<GameNews> getGameNews(@RequestParam("limit") int limit);

    @GetMapping("/search")
    List<GameNews> searchNews(@RequestParam("title") String title);
}
