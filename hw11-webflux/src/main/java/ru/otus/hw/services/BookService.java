package ru.otus.hw.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.dto.response.BookDtoResponse;

public interface BookService {
    Mono<BookDtoResponse> findById(long id);

    Flux<BookDtoResponse> findAll();

    Mono<Book> insert(String title, long authorId, long genreId);

    Mono<Book> update(long id, String title, long authorId, long genreId);

    Mono<Void> deleteById(long id);
}
