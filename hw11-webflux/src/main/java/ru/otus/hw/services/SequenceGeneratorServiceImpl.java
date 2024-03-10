package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.hw.helpers.DatabaseSequence;
import ru.otus.hw.repositories.DatabaseSequenceRepository;

@RequiredArgsConstructor
@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

    private final DatabaseSequenceRepository databaseSequenceRepository;

    public Mono<Long> getNext(String sequenceId) {
        return databaseSequenceRepository.findById(sequenceId)
                .switchIfEmpty(Mono.defer(() -> {
                    var newSequence = new DatabaseSequence(sequenceId, 0L);
                    return databaseSequenceRepository.save(newSequence);
                }))
                .flatMap(sequenceInstance -> {
                    sequenceInstance.increment();
                    return databaseSequenceRepository.save(sequenceInstance);
                })
                .map(DatabaseSequence::getSequence);
    }
}
