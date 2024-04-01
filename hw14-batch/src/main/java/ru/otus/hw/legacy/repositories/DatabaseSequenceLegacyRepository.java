package ru.otus.hw.legacy.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.legacy.models.DatabaseSequenceLegacy;

public interface DatabaseSequenceLegacyRepository extends MongoRepository<DatabaseSequenceLegacy, String> {
}
