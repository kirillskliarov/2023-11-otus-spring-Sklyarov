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
import ru.otus.hw.models.Author;
import ru.otus.hw.models.dto.request.AuthorDtoRequest;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@WebFluxTest(AuthorController.class)
@Import(AuthorService.class)
public class AuthorControllerTest {

    @MockBean
    private AuthorService authorService;

    @Autowired
    private WebTestClient webClient;

    @Test
    void shouldReturnCorrectAuthorList() {
        long authorId1 = 1L;
        long authorId2 = 2L;
        String fullName1 = "Author1";
        String fullName2 = "Author2";
        List<Author> authors = List.of(new Author(authorId1, fullName1), new Author(authorId2, fullName2));
        Mockito.when(authorService.findAll()).thenReturn(Flux.fromIterable(authors));

        webClient.get()
                .uri("/api/authors")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(authorId1)
                .jsonPath("$.[0].fullName").isEqualTo(fullName1)
                .jsonPath("$.[1].id").isEqualTo(authorId2)
                .jsonPath("$.[1].fullName").isEqualTo(fullName2);
    }

    @Test
    void shouldReturnCorrectAuthorById() {
        long authorId = 1L;
        String fullName = "Author1";
        Author author = new Author(authorId, fullName);

        Mockito.when(authorService.findById(authorId)).thenReturn(Mono.just(author));

        webClient.get()
                .uri("/api/authors/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(authorId)
                .jsonPath("$.fullName").isEqualTo(fullName);
    }

    @Test
    void shouldCreateAuthor() {
        long authorId = 1L;
        String fullName = "Author1";
        Author author = new Author(authorId, fullName);
        AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest(fullName);

        Mockito.when(authorService.insert(fullName)).thenReturn(Mono.just(author));

        webClient.post()
                .uri("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(authorDtoRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(authorId)
                .jsonPath("$.fullName").isEqualTo(fullName);
    }
}
