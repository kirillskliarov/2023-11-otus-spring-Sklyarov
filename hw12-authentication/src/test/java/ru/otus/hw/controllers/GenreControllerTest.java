package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.dto.request.GenreDtoRequest;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    public GenreService genreService;

    @Test
    @WithMockUser
    void shouldReturnCorrectGenreList() throws Exception {
        long genreId1 = 1;
        String genreName1 = "Genre1";
        long genreId2 = 2;
        String genreName2 = "Genre2";
        List<Genre> genres = List.of(new Genre(genreId1, genreName1), new Genre(genreId2, genreName2));
        given(genreService.findAll()).willReturn(genres);

        mvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(allOf(
                        containsString(genreName1),
                        containsString(genreName2)))
                );
    }

    @Test
    void shouldDenyGenreList() throws Exception {
        mvc.perform(get("/genres"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    void shouldCreateGenre() throws Exception {
        long genreId = 1;
        String genreName = "Genre1";
        Genre genre = new Genre(genreId, genreName);
        GenreDtoRequest genreDtoRequest = new GenreDtoRequest(genreName);
        given(genreService.insert(any())).willReturn(genre);

        mvc.perform(post("/genres")
                        .with(csrf())
                        .param("name", genreDtoRequest.getName()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void shouldDenyCreationGenre() throws Exception {
        GenreDtoRequest genreDtoRequest = new GenreDtoRequest("Genre1");

        mvc.perform(post("/genres")
                        .with(csrf())
                        .param("name", genreDtoRequest.getName()))
                .andExpect(status().is4xxClientError());
    }
}
