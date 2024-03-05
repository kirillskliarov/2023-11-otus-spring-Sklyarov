package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.dto.response.BookDtoResponse;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Mono<BookDtoResponse> findById(long id) {
        return bookRepository.findById(id)
                .flatMap(book -> Mono.zip(
                                genreRepository.findById(book.getGenreId()),
                                authorRepository.findById(book.getAuthorId()))
                        .map(results -> {
                            Genre genre = results.getT1();
                            Author author = results.getT2();
                            BookDtoResponse bookDto = new BookDtoResponse(book);
                            bookDto.setGenre(genre);
                            bookDto.setAuthor(author);
                            return bookDto;
                        }));
    }

    @Override
    public Flux<BookDtoResponse> findAll() {
        return bookRepository.findAll()
                .flatMap(book -> Mono.zip(
                                genreRepository.findById(book.getGenreId()),
                                authorRepository.findById(book.getAuthorId()))
                        .map(results -> {
                            BookDtoResponse bookDto = new BookDtoResponse(book);
                            bookDto.setGenre(results.getT1());
                            bookDto.setAuthor(results.getT2());
                            return bookDto;
                        }));
    }

    @Override
    @Transactional
    public Mono<Book> insert(String title, long authorId, long genreId) {
        return save(0, title, authorId, genreId);
    }

    @Override
    @Transactional
    public Mono<Book> update(long id, String title, long authorId, long genreId) {
        return save(id, title, authorId, genreId);
    }

    @Override
    public Mono<Void> deleteById(long id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Book with id %d not found".formatted(id))))
                .flatMap(bookRepository::delete);
    }

    private Mono<Book> save(long id, String title, long authorId, long genreId) {
        Mono<Long> idStream = id == 0 ? sequenceGeneratorService.getNext(Book.SEQUENCE_NAME) : Mono.just(id);
        Mono<Author> authorStream = authorRepository.findById(authorId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Author with id %d not found"
                        .formatted(authorId))));
        Mono<Genre> genreStream = genreRepository.findById(genreId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Genre with id %d not found".formatted(genreId))));
        Mono<Book> bookStream = id == 0 ? Mono.just(new Book()).map(book -> {
            book.setComments(List.of());
            return book;
        }) : bookRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Book with id %d not found".formatted(id))));
        return Mono.zip(idStream, authorStream, genreStream, bookStream).map((params) -> {
            long bookId = params.getT1();
            Author author = params.getT2();
            Genre genre = params.getT3();
            Book book = params.getT4();
            book.setId(bookId);
            book.setTitle(title);
            book.setAuthorId(author.getId());
            book.setGenreId(genre.getId());
            return book;
        }).flatMap(bookRepository::save);
    }
}
