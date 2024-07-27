package ru.halcyon.aggregatorservice.support;

import lombok.*;

import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameNewsMessage {
    private String title;
    private String content;
    private String source;
    private Calendar createdAt;
    private List<String> tags;
}
