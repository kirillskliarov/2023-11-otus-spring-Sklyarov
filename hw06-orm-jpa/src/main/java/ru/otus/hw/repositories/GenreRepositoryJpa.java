package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Optional<Genre> findById(long id) {
        var result = em.find(Genre.class, id);
        return Optional.ofNullable(result);
    }
}
