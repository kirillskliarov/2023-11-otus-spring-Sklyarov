package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends ListCrudRepository<Book, Long> {
    Optional<Book> findById(long id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"author", "genre"})
    @Nonnull
    List<Book> findAll();
}
