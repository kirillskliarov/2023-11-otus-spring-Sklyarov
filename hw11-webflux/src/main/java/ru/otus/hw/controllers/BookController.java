package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.dto.request.BookDtoRequest;
import ru.otus.hw.models.dto.request.CommentDtoRequest;
import ru.otus.hw.models.dto.response.BookDtoResponse;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    private final CommentService commentService;

    @GetMapping("/api/books")
    public Flux<BookDtoResponse> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/api/books/{id}")
    public Mono<BookDtoResponse> getById(@PathVariable long id) {
        return bookService.findById(id);
    }

    @PostMapping("/api/books")
    public Mono<Book> create(@RequestBody BookDtoRequest bookDtoRequest) {
        return bookService.insert(bookDtoRequest.getTitle(), bookDtoRequest.getAuthorId(), bookDtoRequest.getGenreId());
    }

    @PostMapping("/api/books/{id}")
    public Mono<Book> update(@PathVariable long id, @RequestBody BookDtoRequest bookDtoRequest) {
        return bookService
                .update(id, bookDtoRequest.getTitle(), bookDtoRequest.getAuthorId(), bookDtoRequest.getGenreId());
    }

    @PostMapping("/api/books/{id}/comment")
    public Mono<Comment> createComment(@PathVariable long id, @RequestBody CommentDtoRequest commentDtoRequest) {
        return commentService.insert(commentDtoRequest.getText(), id);
    }

    @DeleteMapping("/api/books/{id}")
    public Mono<Void> delete(@PathVariable long id) {
        return bookService.deleteById(id);
    }
}
