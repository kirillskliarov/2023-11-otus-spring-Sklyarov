package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.dto.request.GenreDtoRequest;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    public GenreService genreService;

    @Test
    void shouldReturnCorrectGenreList() throws Exception {
        List<Genre> genres = List.of(new Genre(1, "Genre1"), new Genre(2, "Genre2"));
        given(genreService.findAll()).willReturn(genres);

        List<Genre> expectedResult = new ArrayList<>(genres);

        mvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldReturnCorrectGenreById() throws Exception {
        long genreId = 1L;
        Genre genre = new Genre(genreId, "Genre1");
        given(genreService.findById(genreId)).willReturn(Optional.of(genre));

        mvc.perform(get("/api/genres/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genre)));
    }

    @Test
    void shouldCreateGenre() throws Exception {
        Genre genre = new Genre(1, "Genre1");
        GenreDtoRequest genreDtoRequest = new GenreDtoRequest("Genre1");
        given(genreService.insert(any())).willReturn(genre);
        String expectedResult = mapper.writeValueAsString(genreDtoRequest);

        mvc.perform(post("/api/genres").contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }
}
