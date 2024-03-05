package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.dto.request.GenreDtoRequest;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/api/genres")
    public List<Genre> getAll() {
        return genreService.findAll().stream().toList();
    }

    @GetMapping("/api/genres/{id}")
    public Optional<Genre> getById(@PathVariable long id) {
        return genreService.findById(id);
    }

    @PostMapping("/api/genres")
    public Genre create(@RequestBody GenreDtoRequest genreDtoRequest) {
        return genreService.insert(genreDtoRequest.getName());
    }
}
