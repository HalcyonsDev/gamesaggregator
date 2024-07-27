package ru.halcyon.telegrambot.bot;

public enum BotAnswers {
    START_MESSAGE("***Привет! Я агрегатор игровых новостей!*** Вот, что я умею:\n\nℹ\uFE0F Собирать свежие новости из игровой индустрии из разных источников\n\uD83C\uDD95 Присылать самые свежие новости\n\uD83D\uDD0E Искать новости"),
    NEWS_MESSAGE("%s\n\n%s%sИсточник: %s\n\n@GamesNewsAggregatorBot");

    private String answer;

    BotAnswers(String answer) {
        this.answer = answer;
    }

    public String answer() {
        return answer;
    }
}
