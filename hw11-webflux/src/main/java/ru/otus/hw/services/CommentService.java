package ru.otus.hw.services;

import reactor.core.publisher.Mono;
import ru.otus.hw.models.Comment;

public interface CommentService {
    Mono<Comment> insert(String text, long bookId);
}
