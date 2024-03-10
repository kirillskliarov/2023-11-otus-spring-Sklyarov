package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.dto.request.AuthorDtoRequest;
import ru.otus.hw.services.AuthorService;

@RequiredArgsConstructor
@RestController
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/api/authors")
    public Flux<Author> getAll() {
        return authorService.findAll();
    }

    @GetMapping("/api/authors/{id}")
    public Mono<Author> getById(@PathVariable long id) {
        return authorService.findById(id);
    }

    @PostMapping("/api/authors")
    public Mono<Author> create(@RequestBody AuthorDtoRequest authorDtoRequest) {
        return authorService.insert(authorDtoRequest.getFullName());
    }
}
