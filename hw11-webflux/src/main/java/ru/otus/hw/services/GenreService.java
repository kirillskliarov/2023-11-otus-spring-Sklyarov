package ru.otus.hw.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Genre;

public interface GenreService {
    Flux<Genre> findAll();

    Mono<Genre> findById(long id);

    Mono<Genre> insert(String name);
}
