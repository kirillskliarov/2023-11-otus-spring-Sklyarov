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
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.dto.request.BookDtoRequest;
import ru.otus.hw.models.dto.request.CommentDtoRequest;
import ru.otus.hw.models.dto.response.BookDtoResponse;
import ru.otus.hw.security.SecurityConfig;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(SecurityConfig.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    public BookService bookService;

    @MockBean
    public AuthorService authorService;

    @MockBean
    public GenreService genreService;

    @MockBean
    public CommentService commentService;

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    void shouldReturnCorrectBookList() throws Exception {
        long bookId1 = 1L;
        long bookId2 = 2L;
        String bookTitle1 = "Book1";
        String bookTitle2 = "Book2";
        Author author1 = new Author(1L, "Author1");
        Author author2 = new Author(2L, "Author2");
        Genre genre1 = new Genre(1L, "Genre1");
        Genre genre2 = new Genre(2L, "Genre2");
        List<BookDtoResponse> books = List.of(
                new BookDtoResponse(new Book(bookId1, bookTitle1, author1, genre1, List.of())),
                new BookDtoResponse(new Book(bookId2, bookTitle2, author2, genre2, List.of())));
        given(bookService.findAll()).willReturn(books);

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(allOf(
                        containsString(bookTitle1),
                        containsString(bookTitle2)))
                );
    }

    @Test
    void shouldDenyBookList() throws Exception {
        mvc.perform(get("/books"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(LOCATION, containsString("/login")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    void shouldReturnCorrectBookById() throws Exception {
        long bookId = 1L;
        String bookTitle = "Book1";
        Author author1 = new Author(1, "Author1");
        Genre genre1 = new Genre(1, "Genre1");
        Book book = new Book(bookId, "Book1", author1, genre1, List.of());
        given(bookService.findById(bookId)).willReturn(Optional.of(book));

        mvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString(bookTitle)));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ANONYMOUS"})
    void shouldDenyBookByIdForNonPermittedUser() throws Exception {
        mvc.perform(get("/books/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldDenyBookById() throws Exception {
        mvc.perform(get("/books/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(LOCATION, containsString("/login")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void shouldCreateBook() throws Exception {
        long bookId = 1L;
        String bookTitle = "Book1";
        long authorId = 1L;
        long genreId = 1L;
        BookDtoRequest bookDtoRequest = new BookDtoRequest(bookTitle, authorId, genreId);
        Author author = new Author(genreId, "Author1");
        Genre genre = new Genre(genreId, "Genre1");
        Book book = new Book(bookId, bookTitle, author, genre, List.of());
        given(bookService.insert(bookTitle, authorId, genreId)).willReturn(book);

        mvc.perform(post("/books")
                        .with(csrf())
                        .param("title", bookDtoRequest.getTitle())
                        .param("authorId", String.valueOf(bookDtoRequest.getAuthorId()))
                        .param("genreId", String.valueOf(bookDtoRequest.getGenreId()))
                )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    void shouldDenyCreatingBookForNonPermittedUser() throws Exception {
        String bookTitle = "Book1";
        long authorId = 1L;
        long genreId = 1L;
        BookDtoRequest bookDtoRequest = new BookDtoRequest(bookTitle, authorId, genreId);

        mvc.perform(post("/books")
                        .with(csrf())
                        .param("title", bookDtoRequest.getTitle())
                        .param("authorId", String.valueOf(bookDtoRequest.getAuthorId()))
                        .param("genreId", String.valueOf(bookDtoRequest.getGenreId()))
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldDenyCreatingBook() throws Exception {
        String bookTitle = "Book1";
        long authorId = 1L;
        long genreId = 1L;
        BookDtoRequest bookDtoRequest = new BookDtoRequest(bookTitle, authorId, genreId);

        mvc.perform(post("/books")
                        .with(csrf())
                        .param("title", bookDtoRequest.getTitle())
                        .param("authorId", String.valueOf(bookDtoRequest.getAuthorId()))
                        .param("genreId", String.valueOf(bookDtoRequest.getGenreId()))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(LOCATION, containsString("/login")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void shouldUpdateBook() throws Exception {
        long bookId = 1L;
        String bookTitle = "Book1";
        long authorId = 1L;
        long genreId = 1L;
        BookDtoRequest bookDtoRequest = new BookDtoRequest(bookTitle, authorId, genreId);
        Author author = new Author(genreId, "Author1");
        Genre genre = new Genre(genreId, "Genre1");
        Book book = new Book(bookId, bookTitle, author, genre, List.of());
        given(bookService.findById(bookId)).willReturn(Optional.of(book));
        given(bookService.update(bookId, bookTitle, authorId, genreId)).willReturn(book);

        mvc.perform(post("/books/1/edit")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", bookDtoRequest.getTitle())
                        .param("authorId", String.valueOf(bookDtoRequest.getAuthorId()))
                        .param("genreId", String.valueOf(bookDtoRequest.getGenreId()))
                )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    void shouldDenyUpdatingBookForNonPermittedUser() throws Exception {
        String bookTitle = "Book1";
        long authorId = 1L;
        long genreId = 1L;
        BookDtoRequest bookDtoRequest = new BookDtoRequest(bookTitle, authorId, genreId);

        mvc.perform(post("/books/1/edit")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", bookDtoRequest.getTitle())
                        .param("authorId", String.valueOf(bookDtoRequest.getAuthorId()))
                        .param("genreId", String.valueOf(bookDtoRequest.getGenreId()))
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldDenyUpdatingBook() throws Exception {
        String bookTitle = "Book1";
        long authorId = 1L;
        long genreId = 1L;
        BookDtoRequest bookDtoRequest = new BookDtoRequest(bookTitle, authorId, genreId);

        mvc.perform(post("/books/1/edit")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", bookDtoRequest.getTitle())
                        .param("authorId", String.valueOf(bookDtoRequest.getAuthorId()))
                        .param("genreId", String.valueOf(bookDtoRequest.getGenreId()))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(LOCATION, containsString("/login")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    void shouldCreateComment() throws Exception {
        long bookId = 1L;
        CommentDtoRequest commentRequest = new CommentDtoRequest(bookId, "Comment1");

        mvc.perform(post("/books/1/comment")
                        .with(csrf())
                        .param("text", commentRequest.getText()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ANONYMOUS"})
    void shouldDenyCreatingCommentForNonPermittedUser() throws Exception {
        mvc.perform(post("/books/1/comment")
                        .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldDenyCreatingComment() throws Exception {
        mvc.perform(post("/books/1/comment")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(LOCATION, containsString("/login")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void shouldDeleteBook() throws Exception {
        long bookId = 1L;

        mvc.perform(post("/books/" + bookId + "/delete")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    void shouldDenyDeletingBookForNonPermittedUser() throws Exception {
        long bookId = 1L;

        mvc.perform(post("/books/" + bookId + "/delete")
                        .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldDenyDeletingBook() throws Exception {
        long bookId = 1L;

        mvc.perform(post("/books/" + bookId + "/delete")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(LOCATION, containsString("/login")));
    }

}
