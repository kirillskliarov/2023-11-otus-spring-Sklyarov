package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами")
@DataJpaTest
@Import({BookRepositoryJpa.class})
class BookRepositoryJpaTest {

    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;
    private static final long FIRST_BOOK_ID = 1L;
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long FIRST_GENRE_ID = 1L;

    @Autowired
    private BookRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        var actualBook = repositoryJpa.findById(FIRST_BOOK_ID);
        var expectedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(actualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBookList() {
        var actualBooks = repositoryJpa.findAll();

        assertThat(actualBooks).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(book -> !book.getTitle().isEmpty())
                .allMatch(book -> !book.getGenre().getName().isEmpty())
                .allMatch(book -> !book.getAuthor().getFullName().isEmpty());
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var author = em.find(Author.class, FIRST_AUTHOR_ID);
        var genre = em.find(Genre.class, FIRST_GENRE_ID);
        var expectedBook = new Book(0, "BookTitle_10500", author, genre, List.of());
        var returnedBook = repositoryJpa.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(repositoryJpa.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен сохранять изменённую книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var author = em.find(Author.class, FIRST_AUTHOR_ID);
        var genre = em.find(Genre.class, FIRST_GENRE_ID);
        var expectedBook = new Book(1L, "BookTitle_10500", author, genre, List.of());

        assertThat(repositoryJpa.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedBook);

        var returnedBook = repositoryJpa.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(repositoryJpa.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        var book = repositoryJpa.findById(1L);
        assertThat(book).isPresent();
        repositoryJpa.delete(book.get());
        assertThat(repositoryJpa.findById(1L)).isEmpty();
    }
}
