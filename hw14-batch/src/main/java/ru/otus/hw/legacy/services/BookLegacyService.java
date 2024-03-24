package ru.otus.hw.legacy.services;

import ru.otus.hw.legacy.models.BookLegacy;

import java.util.List;
import java.util.Optional;

public interface BookLegacyService {
    Optional<BookLegacy> findById(long id);

    List<BookLegacy> findAll();

    BookLegacy insert(String title, long authorId, long genreId);

    BookLegacy update(long id, String title, long authorId, long genreId);

    void deleteById(long id);
}
