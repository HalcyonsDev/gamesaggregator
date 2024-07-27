package ru.halcyon.aggregatorservice;

import ru.halcyon.aggregatorservice.model.GameNews;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class GameNewsHelper {
    public static List<GameNews> getTestNews(int n) {
        List<GameNews> testGameNews = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            testGameNews.add(getTestOneNews());
        }

        return testGameNews;
    }

    public static GameNews getTestOneNews() {
        return GameNews.builder()
                .title("test_title")
                .content("test_content")
                .createdAt(new GregorianCalendar())
                .tags(new ArrayList<>())
                .source("test_source")
                .build();
    }
}
