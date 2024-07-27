package ru.halcyon.telegrambot.support;

import lombok.*;

import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameNews {
    private Long id;
    private String title;
    private String content;
    private String source;
    private Calendar createdAt;
    private List<String> tags;
}
