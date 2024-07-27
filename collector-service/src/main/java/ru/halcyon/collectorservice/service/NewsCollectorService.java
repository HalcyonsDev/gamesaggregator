package ru.halcyon.collectorservice.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.halcyon.collectorservice.support.GameNews;
import ru.halcyon.collectorservice.support.Classes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class NewsCollectorService {
    private final String[] months = new String[] {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октебря", "ноября", "декабря"};

    private final NewsProducer newsProducer;

    @Scheduled(fixedRate =  3 * 60 * 60 * 1000) // 3 hours
    public void collectAndPublishNews() {
        try {
            newsProducer.sendNews(parseNewsFromStopgame().get());
            newsProducer.sendNews(parseNewsFromGamemag().get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    CompletableFuture<List<GameNews>> parseNewsFromStopgame() {
        String url = "https://stopgame.ru";

        try {
            Document document = Jsoup.connect(url + "/news").get();
            Elements elements = document.getElementsByClass(Classes.STOPGAME_NEWS.getValue());

            List<GameNews> gameNewsList = new ArrayList<>();
            for (Element element: elements) {
                String href = element.attr("href");

                Document newsDocument = Jsoup.connect(url + href).get();

                GameNews gameNews = GameNews.builder()
                        .title(element.text())
                        .content(newsDocument.getElementsByClass(Classes.STOPGAME_CONTENT.getValue()).text())
                        .source(url + href)
                        .createdAt(toStopgameCalendar(newsDocument.getElementsByClass(Classes.STOPGAME_DATE.getValue()).text()))
                        .tags(getTagsFromStopgame(newsDocument))
                        .build();

                gameNewsList.add(gameNews);
            }

            return CompletableFuture.completedFuture(gameNewsList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    CompletableFuture<List<GameNews>> parseNewsFromGamemag() {
        String url = "https://gamemag.ru";

        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass(Classes.GAMEMAG_NEWS.getValue());

            List<GameNews> gameNewsList = new ArrayList<>();
            for (Element element: elements) {
                String href = element.attr("href");
                Document newsDocument = Jsoup.connect(url + href).get();
                String date = newsDocument.getElementsByClass(Classes.GAMEMAG_DATE.getValue()).text();

                System.out.println(date);
                GameNews gameNews = GameNews.builder()
                        .title(newsDocument.getElementsByClass(Classes.GAMEMAG_TITLE.getValue()).text())
                        .content(newsDocument.getElementsByClass(Classes.GAMEMAG_CONTENT.getValue()).text())
                        .source(url + href)
                        .createdAt(toGamemagCalendar(date.substring(0, 5)))
                        .build();

                gameNewsList.add(gameNews);
            }

            return CompletableFuture.completedFuture(gameNewsList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Calendar toStopgameCalendar(String date) {
        Calendar calendar = new GregorianCalendar();

        if (date.equalsIgnoreCase("сегодня")) {
            return setZeroTime(calendar);
        } else if (date.equalsIgnoreCase("вчера")) {
            calendar.setTime(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
            return setZeroTime(calendar);
        }

        int day = Integer.parseInt(date.split(" ")[0]);
        String month = date.split(" ")[1];
        int monthValue = 0;

        for (int i = 1; i < 12; i++) {
            if (month.equalsIgnoreCase(months[i])) {
                monthValue = i;
                break;
            }
        }

        calendar.set(2024, monthValue, day);
        return calendar;
    }

    private Calendar toGamemagCalendar(String date) {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("d.MM");

        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return calendar;
    }

    private Calendar setZeroTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }

    private List<String> getTagsFromStopgame(Document document) {
        Elements tagsElements = document.getElementsByClass(Classes.STOPGAME_TAGS.getValue());
        return tagsElements.eachText();
    }
}
