package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.helpers.DatabaseSequence;
import ru.otus.hw.repositories.DatabaseSequenceRepository;

@RequiredArgsConstructor
@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

    private final DatabaseSequenceRepository databaseSequenceRepository;

    public long getNext(String sequenceId) {
        var sequence = databaseSequenceRepository.findById(sequenceId);
        if (sequence.isEmpty()) {
            var newSequence = new DatabaseSequence(sequenceId, 1L);
            databaseSequenceRepository.save(newSequence);
            return newSequence.getSequence();
        }

        var sequenceInstance = sequence.get();
        sequenceInstance.increment();
        databaseSequenceRepository.save(sequenceInstance);

        return sequenceInstance.getSequence();
    }
}
