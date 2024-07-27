package ru.halcyon.collectorservice.support;

public enum Classes {
    STOPGAME_NEWS("_title_1vlem_60"),
    STOPGAME_CONTENT("_content_12po9_13"),
    STOPGAME_DATE("_date_12po9_591 _date--full_12po9_1"),
    STOPGAME_TAGS("_tag_12po9_1558"),

    GAMEMAG_NEWS("news-item__text"),
    GAMEMAG_TITLE("overview__title"),
    GAMEMAG_CONTENT("content-text"),
    GAMEMAG_DATE("date__names");

    private final String value;

    Classes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
