package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.dto.request.AuthorDtoRequest;
import ru.otus.hw.security.SecurityConfig;
import ru.otus.hw.services.AuthorService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@Import(SecurityConfig.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    public AuthorService authorService;

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    void shouldReturnCorrectAuthorList() throws Exception {
        long authorId1 = 1;
        String authorFullName1 = "Author1";
        long authorId2 = 2;
        String authorFullName2 = "Author2";
        List<Author> authors = List.of(new Author(authorId1, authorFullName1), new Author(authorId2, authorFullName2));
        given(authorService.findAll()).willReturn(authors);

        mvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(allOf(
                        containsString(authorFullName1),
                        containsString(authorFullName2)))
                );
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ANONYMOUS"})
    void shouldDenyAuthorListForNonPermittedUser() throws Exception {
        mvc.perform(get("/authors"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldDenyAuthorList() throws Exception {
        mvc.perform(get("/authors"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(LOCATION, containsString("/login")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void shouldCreateAuthor() throws Exception {
        Author author = new Author(1, "Author1");
        AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest("Author1");
        given(authorService.insert(authorDtoRequest.getFullName())).willReturn(author);

        mvc.perform(post("/authors")
                        .with(csrf())
                        .param("fullName", authorDtoRequest.getFullName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(LOCATION, containsString("/authors")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_User"})
    void shouldDenyCreationAuthorForGeneralUser() throws Exception {
        AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest("Author1");

        mvc.perform(post("/authors")
                        .with(csrf())
                        .param("fullName", authorDtoRequest.getFullName()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldDenyCreationAuthor() throws Exception {
        AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest("Author1");

        mvc.perform(post("/authors")
                        .with(csrf())
                        .param("fullName", authorDtoRequest.getFullName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(LOCATION, containsString("/login")))
        ;
    }
}
