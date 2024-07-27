package ru.halcyon.collectorservice.support;

import lombok.*;

import java.util.Calendar;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameNews {
    private String title;
    private String content;
    private String source;
    private Calendar createdAt;
    private List<String> tags;
}
