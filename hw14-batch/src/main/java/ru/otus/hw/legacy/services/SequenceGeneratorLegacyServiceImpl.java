package ru.otus.hw.legacy.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.legacy.models.DatabaseSequenceLegacy;
import ru.otus.hw.legacy.repositories.DatabaseSequenceLegacyRepository;

@RequiredArgsConstructor
@Service
public class SequenceGeneratorLegacyServiceImpl implements SequenceGeneratorLegacyService {

    private final DatabaseSequenceLegacyRepository databaseSequenceRepository;

    public long getNext(String sequenceId) {
        var sequence = databaseSequenceRepository.findById(sequenceId);
        if (sequence.isEmpty()) {
            var newSequence = new DatabaseSequenceLegacy(sequenceId, 1L);
            databaseSequenceRepository.save(newSequence);
            return newSequence.getSequence();
        }

        var sequenceInstance = sequence.get();
        sequenceInstance.increment();
        databaseSequenceRepository.save(sequenceInstance);

        return sequenceInstance.getSequence();
    }
}
