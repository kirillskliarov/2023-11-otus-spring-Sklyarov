package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import({AuthorServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayName("Should execute test for AuthorServiceImpl")
public class AuthorServiceImplTest {
    @MockBean
    public AuthorRepository authorRepository;

    @MockBean
    public SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    @InjectMocks
    public AuthorServiceImpl authorService;

    @Test
    @DisplayName("should return all authors")
    void shouldReturnAllAuthors() {
        // Arrange
        List<Author> expectedAuthors = List.of(new Author(1, "Author_1"));
        var expectedSize = expectedAuthors.size();
        Mockito.when(authorRepository.findAll()).thenReturn(expectedAuthors);

        // Act
        var authors = authorService.findAll();

        // Assert
        assertThat(authors).isNotNull().hasSize(expectedSize).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }

    @Test
    @DisplayName("should return author by id")
    void shouldReturnAuthorById() {
        // Arrange
        long authorId = 1;
        var expectedAuthor = new Author(authorId, "Author_1");
        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(expectedAuthor));

        // Act
        var author = authorService.findById(authorId);

        // Assert
        assertThat(author).isPresent().get().usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("should insert author")
    void shouldInsertAuthor() {
        // Arrange
        long authorId = 1;
        String authorName = "Author_1";
        var expectedAuthor = new Author(authorId, authorName);
        Mockito.when(sequenceGeneratorService.getNext(Author.SEQUENCE_NAME)).thenReturn(authorId);

        // Act
        var author = authorService.insert(authorName);

        // Assert
        verify(authorRepository, times(1)).save(expectedAuthor);
    }
}
