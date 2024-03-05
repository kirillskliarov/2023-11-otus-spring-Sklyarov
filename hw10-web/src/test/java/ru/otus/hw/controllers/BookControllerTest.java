package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.dto.request.BookDtoRequest;
import ru.otus.hw.models.dto.response.BookDtoResponse;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    public BookService bookService;

    @MockBean
    public CommentService commentService;

    @Test
    void shouldReturnCorrectBookList() throws Exception {
        var author1 = new Author(1, "Author1");
        var author2 = new Author(2, "Author2");
        var genre1 = new Genre(1, "Genre1");
        var genre2 = new Genre(2, "Genre2");
        List<BookDtoResponse> books = List.of(
                new BookDtoResponse(new Book(1, "Book1", author1, genre1, List.of())),
                new BookDtoResponse(new Book(2, "Book2", author2, genre2, List.of())));
        given(bookService.findAll()).willReturn(books);

        List<BookDtoResponse> expectedResult = new ArrayList<>(books);

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldReturnCorrectBookById() throws Exception {
        long bookId = 1L;
        var author1 = new Author(1, "Author1");
        var genre1 = new Genre(1, "Genre1");
        Book book = new Book(bookId, "Book1", author1, genre1, List.of());
        given(bookService.findById(bookId)).willReturn(Optional.of(book));

        mvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(book)));
    }

    @Test
    void shouldCreateBook() throws Exception {
        long bookId = 1L;
        String bookTitle = "Book1";
        long authorId = 1L;
        long genreId = 1L;
        BookDtoRequest bookDtoRequest = new BookDtoRequest(0, bookTitle, authorId, authorId);

        Author author = new Author(genreId, "Author1");
        Genre genre = new Genre(genreId, "Genre1");
        Book book = new Book(bookId, bookTitle, author, genre, List.of());
        given(bookService.insert(bookTitle, authorId, genreId)).willReturn(book);

        String request = mapper.writeValueAsString(bookDtoRequest);
        String expectedResult = mapper.writeValueAsString(book);

        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        long bookId = 1L;
        String bookTitle = "Book1";
        long authorId = 1L;
        long genreId = 1L;
        BookDtoRequest bookDtoRequest = new BookDtoRequest(bookId, bookTitle, authorId, authorId);

        Author author = new Author(genreId, "Author1");
        Genre genre = new Genre(genreId, "Genre1");
        Book book = new Book(bookId, bookTitle, author, genre, List.of());
        given(bookService.update(bookId, bookTitle, authorId, genreId)).willReturn(book);

        String request = mapper.writeValueAsString(bookDtoRequest);
        String expectedResult = mapper.writeValueAsString(book);

        mvc.perform(post("/api/books/1")
                        .contentType(APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        long bookId = 1L;

        mvc.perform(delete("/api/books/" + bookId)
                        .contentType(APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk());
    }

}
