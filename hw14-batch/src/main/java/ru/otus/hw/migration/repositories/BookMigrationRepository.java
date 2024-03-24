package ru.otus.hw.migration.repositories;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.hw.migration.models.BookMigration;

public interface BookMigrationRepository extends ListCrudRepository<BookMigration, Long> {
}
