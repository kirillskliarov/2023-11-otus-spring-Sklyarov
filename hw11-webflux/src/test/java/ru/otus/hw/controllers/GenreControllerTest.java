package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.dto.request.GenreDtoRequest;
import ru.otus.hw.services.GenreService;

import java.util.List;

@WebFluxTest(GenreController.class)
@Import(GenreService.class)
public class GenreControllerTest {

    @MockBean
    private GenreService genreService;

    @Autowired
    private WebTestClient webClient;

    @Test
    void shouldReturnCorrectGenreList() {
        long genreId1 = 1L;
        long genreId2 = 2L;
        String name1 = "Genre1";
        String name2 = "Genre2";
        List<Genre> genres = List.of(new Genre(genreId1, name1), new Genre(genreId2, name2));
        Mockito.when(genreService.findAll()).thenReturn(Flux.fromIterable(genres));

        webClient.get()
                .uri("/api/genres")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(genreId1)
                .jsonPath("$.[0].name").isEqualTo(name1)
                .jsonPath("$.[1].id").isEqualTo(genreId2)
                .jsonPath("$.[1].name").isEqualTo(name2);
    }

    @Test
    void shouldReturnCorrectGenreById() {
        long genreId = 1L;
        String fullName = "Genre1";
        Genre genre = new Genre(genreId, fullName);

        Mockito.when(genreService.findById(genreId)).thenReturn(Mono.just(genre));

        webClient.get()
                .uri("/api/genres/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(genreId)
                .jsonPath("$.name").isEqualTo(fullName);
    }

    @Test
    void shouldCreateGenre() {
        long genreId = 1L;
        String fullName = "Genre1";
        Genre genre = new Genre(genreId, fullName);
        GenreDtoRequest genreDtoRequest = new GenreDtoRequest(fullName);

        Mockito.when(genreService.insert(fullName)).thenReturn(Mono.just(genre));

        webClient.post()
                .uri("/api/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(genreDtoRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(genreId)
                .jsonPath("$.name").isEqualTo(fullName);
    }
}
