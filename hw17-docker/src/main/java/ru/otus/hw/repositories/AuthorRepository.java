package ru.otus.hw.repositories;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.hw.models.Author;

import java.util.Optional;

public interface AuthorRepository extends ListCrudRepository<Author, Long> {
    Optional<Author> findById(long id);
}
