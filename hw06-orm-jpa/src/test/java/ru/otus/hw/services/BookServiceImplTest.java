package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import({BookServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayName("Should execute test for BookServiceImpl")
public class BookServiceImplTest {
    @MockBean
    public AuthorRepository authorRepository;

    @MockBean
    @SpyBean
    public BookRepository bookRepository;

    @MockBean
    public GenreRepository genreRepository;

    @Autowired
    @InjectMocks
    public BookServiceImpl bookService;

    @Test
    @DisplayName("should return all books")
    void shouldReturnAllBooks() {
        // Arrange
        var author = new Author(1, "Author_1");
        var genre = new Genre(1, "Genre_1");
        List<Book> expectedBooks = List.of(new Book(1, "Book_1", author, genre));
        var expectedSize = expectedBooks.size();
        Mockito.when(bookRepository.findAll()).thenReturn(expectedBooks);

        // Act
        var books = bookService.findAll();

        // Assert
        assertThat(books).isNotNull().hasSize(expectedSize).usingRecursiveComparison().isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("should return book by id")
    void shouldReturnBookById() {
        // Arrange
        var author = new Author(1, "Author_1");
        var genre = new Genre(1, "Genre_1");
        long bookId = 1;
        var expectedBook = new Book(1, "Book_1", author, genre);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(expectedBook));

        // Act
        var book = bookService.findById(bookId);

        // Assert
        assertThat(book).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should insert book")
    public void shouldInsertBook() {
        // Arrange
        long authorId = 1;
        var author = new Author(authorId, "Author_1");
        long genreId = 1;
        var genre = new Genre(genreId, "Genre_1");
        var bookTitle = "Book_1";
        var book = new Book(0, bookTitle, author, genre);
        var expectedBook = new Book(1, bookTitle, author, genre);
        Mockito.when(bookRepository.save(book)).thenReturn(expectedBook);
        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        Mockito.when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));

        // Act
        var insertedBook = bookService.insert(bookTitle, authorId, genreId);

        // Assert
        assertThat(insertedBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should update book")
    public void shouldUpdateBook() {
        // Arrange
        long authorId = 1;
        var author = new Author(authorId, "Author_1");
        long genreId = 1;
        var genre = new Genre(genreId, "Genre_1");
        var bookTitle = "Book_1";
        long bookId = 1;
        var book = new Book(bookId, bookTitle, author, genre);
        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        Mockito.when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        // Act
        var updatedBook = bookService.update(bookId, bookTitle, authorId, genreId);

        // Assert
        assertThat(updatedBook).usingRecursiveComparison().isEqualTo(book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("should delete book")
    void shouldDeleteBook() {
        // Arrange
        var author = new Author(1, "Author_1");
        var genre = new Genre(1, "Genre_1");
        long bookId = 1;
        var book = new Book(1, "Book_1", author, genre);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        bookService.deleteById(bookId);

        // Assert
        verify(bookRepository, times(1)).delete(book);
    }
}
