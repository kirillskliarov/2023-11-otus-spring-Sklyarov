package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final BookRepository bookRepository;

    private final SequenceGeneratorService sequenceGeneratorService;

    private static Comment getCommentById(Book book, long commentId) {
        return book.getComments().stream()
                .filter(c -> c.getId() == commentId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<Comment> findById(long id) {
        var book = bookRepository.findBookContainsCommentByCommentId(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));

        var comment = getCommentById(book, id);

        return Optional.ofNullable(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findCommentsByBookId(long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        return book.getComments().stream().toList();
    }

    @Override
    @Transactional
    public Comment insert(String text, long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        var id = sequenceGeneratorService.getNext(Comment.SEQUENCE_NAME);
        var comment = new Comment(id, text);
        book.getComments().add(comment);
        bookRepository.save(book);
        return comment;
    }

    @Override
    @Transactional
    public Comment update(long id, String text) {
        var book = bookRepository.findBookContainsCommentByCommentId(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));

        var comment = getCommentById(book, id);
        comment.setText(text);
        bookRepository.save(book);
        return comment;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        var book = bookRepository.findBookContainsCommentByCommentId(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        var comments = book.getComments().stream()
                .filter(c -> c.getId() != id)
                .toList();
        book.setComments(comments);
        bookRepository.save(book);
    }
}
