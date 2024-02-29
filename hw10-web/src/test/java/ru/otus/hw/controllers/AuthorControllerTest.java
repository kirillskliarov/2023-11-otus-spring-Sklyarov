package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.dto.request.AuthorDtoRequest;
import ru.otus.hw.services.AuthorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    public AuthorService authorService;

    @Test
    void shouldReturnCorrectAuthorList() throws Exception {
        List<Author> authors = List.of(new Author(1, "Author1"), new Author(2, "Author2"));
        given(authorService.findAll()).willReturn(authors);

        List<Author> expectedResult = new ArrayList<>(authors);

        mvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldReturnCorrectAuthorById() throws Exception {
        long authorId = 1L;
        Author author = new Author(authorId, "Author1");
        given(authorService.findById(authorId)).willReturn(Optional.of(author));

        mvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(author)));
    }

    @Test
    void shouldCreateAuthor() throws Exception {
        Author author = new Author(1, "Author1");
        AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest("Author1");
        given(authorService.insert(any())).willReturn(author);
        String expectedResult = mapper.writeValueAsString(authorDtoRequest);

        mvc.perform(post("/api/authors").contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }
}
