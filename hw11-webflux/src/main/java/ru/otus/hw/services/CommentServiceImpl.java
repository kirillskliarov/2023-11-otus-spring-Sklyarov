package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final BookRepository bookRepository;

    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    @Transactional
    public Mono<Comment> insert(String text, long bookId) {
        Mono<Book> bookStream = bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Book with id %d not found".formatted(bookId))));
        Mono<Long> sequenceStream = sequenceGeneratorService.getNext(Comment.SEQUENCE_NAME);

        return Mono.zip(bookStream, sequenceStream).flatMap(values -> {
            Book book = values.getT1();
            long sequence = values.getT2();
            Comment comment = new Comment(sequence, text);
            book.getComments().add(comment);
            return bookRepository.save(book).map(b -> comment);
        });
    }

}
