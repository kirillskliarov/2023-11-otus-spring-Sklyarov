package ru.otus.hw.legacy.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.legacy.models.AuthorLegacy;
import ru.otus.hw.legacy.repositories.AuthorLegacyRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorLegacyServiceImpl implements AuthorLegacyService {
    private final AuthorLegacyRepository authorRepository;

    private final SequenceGeneratorLegacyService sequenceGeneratorService;

    @Override
    public List<AuthorLegacy> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<AuthorLegacy> findById(long id) {
        return authorRepository.findById(id);
    }

    @Override
    @Transactional
    public AuthorLegacy insert(String fullName) {
        var nextSeq = sequenceGeneratorService.getNext(AuthorLegacy.SEQUENCE_NAME);
        var author = new AuthorLegacy(nextSeq, fullName);
        return authorRepository.save(author);
    }
}
