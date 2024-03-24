package ru.otus.hw.migration.repositories;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.hw.migration.models.AuthorMigration;

import java.util.Optional;

public interface AuthorMigrationRepository extends ListCrudRepository<AuthorMigration, Long> {
    Optional<AuthorMigration> findByLegacyId(long id);
}
