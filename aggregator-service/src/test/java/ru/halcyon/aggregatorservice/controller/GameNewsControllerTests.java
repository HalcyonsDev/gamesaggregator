package ru.halcyon.aggregatorservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.halcyon.aggregatorservice.service.GameNewsService;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.halcyon.aggregatorservice.GameNewsHelper.*;

@WebMvcTest(GameNewsController.class)
public class GameNewsControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameNewsService gameNewsService;

    @Test
    void getLastNews() throws Exception {
        when(gameNewsService.findLastNews(anyInt(), anyInt())).thenReturn(getTestNews(5));

        mvc.perform(get("/api/v1/games/news")
                .param("offset", "0")
                .param("limit", "5"))
                .andExpect(status().isOk())
                .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    void getById() throws Exception {
        when(gameNewsService.findById(anyLong())).thenReturn(getTestOneNews());

        mvc.perform(get("/api/v1/games/news/{newsId}", 0))
                .andExpect(status().isOk())
                .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void search() throws Exception {
        when(gameNewsService.searchNewsByTitle(anyString())).thenReturn(getTestNews(3));

        mvc.perform(get("/api/v1/games/news/search")
                .param("title", "test_title"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }
}
