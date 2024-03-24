package ru.otus.hw.migration.repositories;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.hw.migration.models.GenreMigration;

import java.util.Optional;

public interface GenreMigrationRepository extends ListCrudRepository<GenreMigration, Long> {
    Optional<GenreMigration> findByLegacyId(long id);
}
