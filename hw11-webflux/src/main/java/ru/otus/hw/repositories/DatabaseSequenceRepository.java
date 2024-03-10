package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.hw.helpers.DatabaseSequence;

public interface DatabaseSequenceRepository extends ReactiveMongoRepository<DatabaseSequence, String> {
}
