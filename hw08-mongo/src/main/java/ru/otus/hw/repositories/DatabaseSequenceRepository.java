package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.helpers.DatabaseSequence;

public interface DatabaseSequenceRepository extends MongoRepository<DatabaseSequence, String> {
}
