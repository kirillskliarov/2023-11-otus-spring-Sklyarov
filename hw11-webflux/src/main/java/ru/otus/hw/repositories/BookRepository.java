package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, Long> {
    @Query("{'comments.id': ?0}")
    Mono<Book> findBookContainsCommentByCommentId(long id);

    Mono<Book> findById(long id);
}
