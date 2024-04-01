package ru.otus.hw.legacy.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.legacy.models.AuthorLegacy;

public interface AuthorLegacyRepository extends MongoRepository<AuthorLegacy, Long> {
}
