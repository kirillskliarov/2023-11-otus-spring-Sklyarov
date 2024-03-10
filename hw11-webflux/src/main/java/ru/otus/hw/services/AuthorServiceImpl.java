package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Flux<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Mono<Author> findById(long id) {
        return authorRepository.findById(id);
    }

    @Override
    @Transactional
    public Mono<Author> insert(String fullName) {
        return sequenceGeneratorService.getNext(Author.SEQUENCE_NAME)
                .map(seq -> new Author(seq, fullName))
                .flatMap(authorRepository::save);
    }
}
