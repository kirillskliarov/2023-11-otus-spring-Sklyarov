package ru.otus.hw.legacy.services;

import ru.otus.hw.legacy.models.GenreLegacy;

import java.util.List;
import java.util.Optional;

public interface GenreLegacyService {
    List<GenreLegacy> findAll();

    Optional<GenreLegacy> findById(long id);

    GenreLegacy insert(String name);
}
