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
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.dto.request.BookDtoRequest;
import ru.otus.hw.models.dto.request.CommentDtoRequest;
import ru.otus.hw.models.dto.response.BookDtoResponse;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

import java.util.List;

@WebFluxTest(BookController.class)
@Import({BookService.class, CommentService.class})
public class BookControllerTest {

    @MockBean
    public BookService bookService;

    @MockBean
    public CommentService commentService;

    @Autowired
    private WebTestClient webClient;

    @Test
    void shouldReturnCorrectBookList() {
        Author author1 = new Author(1, "Author1");
        Author author2 = new Author(2, "Author2");
        Genre genre1 = new Genre(1, "Genre1");
        Genre genre2 = new Genre(2, "Genre2");
        BookDtoResponse bookDtoResponse1 = new BookDtoResponse(
                new Book(1, "Book1", author1.getId(), genre1.getId(), List.of()));
        BookDtoResponse bookDtoResponse2 = new BookDtoResponse(
                new Book(2, "Book2", author2.getId(), genre2.getId(), List.of()));
        bookDtoResponse1.setAuthor(author1);
        bookDtoResponse1.setGenre(genre1);
        bookDtoResponse2.setAuthor(author2);
        bookDtoResponse2.setGenre(genre2);
        List<BookDtoResponse> books = List.of(bookDtoResponse1, bookDtoResponse2);
        Mockito.when(bookService.findAll()).thenReturn(Flux.fromIterable(books));

        webClient.get()
                .uri("/api/books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(bookDtoResponse1.getId())
                .jsonPath("$.[0].title").isEqualTo(bookDtoResponse1.getTitle())
                .jsonPath("$.[1].id").isEqualTo(bookDtoResponse2.getId())
                .jsonPath("$.[1].title").isEqualTo(bookDtoResponse2.getTitle());
    }

    @Test
    void shouldReturnCorrectBookById() throws Exception {
        long bookId = 1L;
        var author = new Author(1, "Author1");
        var genre = new Genre(1, "Genre1");
        BookDtoResponse bookDtoResponse = new BookDtoResponse(
                new Book(bookId, "Book1", author.getId(), genre.getId(), List.of()));
        Mockito.when(bookService.findById(bookId)).thenReturn(Mono.just(bookDtoResponse));

        webClient.get()
                .uri("/api/books/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(bookDtoResponse.getId())
                .jsonPath("$.title").isEqualTo(bookDtoResponse.getTitle());
    }

    @Test
    void shouldCreateBook() {
        long bookId = 1L;
        String bookTitle = "Book1";
        long authorId = 1L;
        long genreId = 1L;
        BookDtoRequest bookDtoRequest = new BookDtoRequest(0, bookTitle, authorId, authorId);

        Author author = new Author(genreId, "Author1");
        Genre genre = new Genre(genreId, "Genre1");
        Book book = new Book(bookId, bookTitle, author.getId(), genre.getId(), List.of());
        Mockito.when(bookService.insert(bookTitle, authorId, genreId)).thenReturn(Mono.just(book));

        webClient.post()
                .uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bookDtoRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(book.getId())
                .jsonPath("$.title").isEqualTo(book.getTitle());
    }

    @Test
    void shouldUpdateBook() {
        long bookId = 1L;
        String bookTitle = "Book1";
        long authorId = 1L;
        long genreId = 1L;
        BookDtoRequest bookDtoRequest = new BookDtoRequest(0, bookTitle, authorId, authorId);

        Author author = new Author(genreId, "Author1");
        Genre genre = new Genre(genreId, "Genre1");
        Book book = new Book(bookId, bookTitle, author.getId(), genre.getId(), List.of());
        Mockito.when(bookService.update(bookId, bookTitle, authorId, genreId)).thenReturn(Mono.just(book));

        webClient.post()
                .uri("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bookDtoRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(book.getId())
                .jsonPath("$.title").isEqualTo(book.getTitle());
    }

    @Test
    void shouldCreateComment() {
        long bookId = 1L;
        String text = "Comment1";
        CommentDtoRequest commentDtoRequest = new CommentDtoRequest(text);
        Comment comment = new Comment(1L, text);
        Mockito.when(commentService.insert(text, bookId)).thenReturn(Mono.just(comment));

        webClient.post()
                .uri("/api/books/1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(commentDtoRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(comment.getId())
                .jsonPath("$.text").isEqualTo(comment.getText());
    }

    @Test
    void shouldDeleteBook() {
        webClient.delete()
                .uri("/api/books/1")
                .exchange()
                .expectStatus().isOk();
    }

}
