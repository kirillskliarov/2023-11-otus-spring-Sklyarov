package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.hw.models.Comment;

import java.util.Optional;

public interface CommentRepository extends ReactiveMongoRepository<Comment, Long> {
    Optional<Comment> findById(long id);
}
