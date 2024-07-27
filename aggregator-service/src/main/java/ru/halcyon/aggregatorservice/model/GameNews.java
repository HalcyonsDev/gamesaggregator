package ru.halcyon.aggregatorservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "game_news")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "source")
    private String source;

    @Column(name = "created_at")
    private Calendar createdAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "tags")
    private List<String> tags;
}
