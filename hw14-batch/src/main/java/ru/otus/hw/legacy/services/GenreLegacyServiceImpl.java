package ru.otus.hw.legacy.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.legacy.models.GenreLegacy;
import ru.otus.hw.legacy.repositories.GenreLegacyRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreLegacyServiceImpl implements GenreLegacyService {
    private final GenreLegacyRepository genreRepository;

    private final SequenceGeneratorLegacyService sequenceGeneratorService;

    @Override
    public List<GenreLegacy> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<GenreLegacy> findById(long id) {
        return genreRepository.findById(id);
    }

    @Override
    @Transactional
    public GenreLegacy insert(String name) {
        var nextSeq = sequenceGeneratorService.getNext(GenreLegacy.SEQUENCE_NAME);
        var author = new GenreLegacy(nextSeq, name);
        return genreRepository.save(author);
    }
}
