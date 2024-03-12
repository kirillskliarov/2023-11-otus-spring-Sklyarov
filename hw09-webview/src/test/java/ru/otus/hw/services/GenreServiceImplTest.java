package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import({GenreServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayName("Should execute test for GenreServiceImpl")
public class GenreServiceImplTest {
    @MockBean
    public GenreRepository genreRepository;

    @Autowired
    @InjectMocks
    public GenreServiceImpl genreService;

    @Test
    @DisplayName("should return all genres")
    void shouldReturnAllGenres() {
        // Arrange
        List<Genre> expectedGenres = List.of(new Genre(1, "Genre_1"));
        var expectedSize = expectedGenres.size();
        Mockito.when(genreRepository.findAll()).thenReturn(expectedGenres);

        // Act
        var genres = genreService.findAll();

        // Assert
        assertThat(genres).isNotNull().hasSize(expectedSize).usingRecursiveComparison().isEqualTo(expectedGenres);
    }

    @Test
    @DisplayName("should return genre by id")
    void shouldReturnGenreById() {
        // Arrange
        long genreId = 1;
        var expectedGenre = new Genre(genreId, "Genre_1");
        Mockito.when(genreRepository.findById(genreId)).thenReturn(Optional.of(expectedGenre));

        // Act
        var genre = genreService.findById(genreId);

        // Assert
        assertThat(genre).isPresent().get().usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}
