package ru.otus.hw.legacy.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.legacy.models.BookLegacy;
import ru.otus.hw.legacy.repositories.AuthorLegacyRepository;
import ru.otus.hw.legacy.repositories.BookLegacyRepository;
import ru.otus.hw.legacy.repositories.GenreLegacyRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookLegacyServiceImpl implements BookLegacyService {
    private final AuthorLegacyRepository authorRepository;

    private final GenreLegacyRepository genreRepository;

    private final BookLegacyRepository bookRepository;

    private final SequenceGeneratorLegacyService sequenceGeneratorService;

    @Override
    public Optional<BookLegacy> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<BookLegacy> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public BookLegacy insert(String title, long authorId, long genreId) {
        return save(0, title, authorId, genreId);
    }

    @Override
    @Transactional
    public BookLegacy update(long id, String title, long authorId, long genreId) {
        return save(id, title, authorId, genreId);
    }

    @Override
    public void deleteById(long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));

        bookRepository.delete(book);
    }

    private BookLegacy save(long id, String title, long authorId, long genreId) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));
        if (id == 0) {
            id = sequenceGeneratorService.getNext(BookLegacy.SEQUENCE_NAME);
        }
        var book = new BookLegacy(id, title, author, genre);
        return bookRepository.save(book);
    }
}
