package ru.otus.hw.repositories;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.hw.models.Genre;

import java.util.Optional;

public interface GenreRepository extends ListCrudRepository<Genre, Long> {
    Optional<Genre> findById(long id);
}
