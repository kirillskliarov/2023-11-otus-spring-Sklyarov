package ru.otus.hw.legacy.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.legacy.models.BookLegacy;


public interface BookLegacyRepository extends MongoRepository<BookLegacy, Long> {
}
