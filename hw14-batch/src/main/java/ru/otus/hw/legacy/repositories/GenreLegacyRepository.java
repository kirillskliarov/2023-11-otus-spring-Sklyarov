package ru.otus.hw.legacy.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.legacy.models.GenreLegacy;

public interface GenreLegacyRepository extends MongoRepository<GenreLegacy, Long> {
}
