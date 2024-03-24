package ru.otus.hw.legacy.services;

import ru.otus.hw.legacy.models.AuthorLegacy;

import java.util.List;
import java.util.Optional;

public interface AuthorLegacyService {
    List<AuthorLegacy> findAll();

    Optional<AuthorLegacy> findById(long id);

    AuthorLegacy insert(String fullName);
}
