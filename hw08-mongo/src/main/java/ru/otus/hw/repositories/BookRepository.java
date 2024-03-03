package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.hw.models.Book;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, Long> {
    @Query("{'comments.id': ?0}")
    Optional<Book> findBookContainsCommentByCommentId(long id);
}
