package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Flux<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Mono<Genre> findById(long id) {
        return genreRepository.findById(id);
    }

    @Override
    @Transactional
    public Mono<Genre> insert(String name) {
        return sequenceGeneratorService.getNext(Genre.SEQUENCE_NAME)
                .map(seq -> new Genre(seq, name))
                .flatMap(genreRepository::save);
    }
}
