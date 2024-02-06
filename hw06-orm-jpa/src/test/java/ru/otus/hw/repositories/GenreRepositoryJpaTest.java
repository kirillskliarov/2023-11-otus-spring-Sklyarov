package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами")
@DataJpaTest
@Import({GenreRepositoryJpa.class})
class GenreRepositoryJpaTest {

    private static final int EXPECTED_NUMBER_OF_GENRES = 3;
    private static final long FIRST_GENRE_ID = 1L;

    @Autowired
    private GenreRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;


    @DisplayName("должен загружать жанр по id")
    @Test
    void shouldReturnCorrectGenreById() {
        var optionalActualGenre = repositoryJpa.findById(FIRST_GENRE_ID);
        var expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenreList() {
        var actualGenres = repositoryJpa.findAll();

        assertThat(actualGenres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES)
                .allMatch(genre -> !genre.getName().isEmpty());
    }
}
