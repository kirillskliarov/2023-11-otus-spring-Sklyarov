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
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import({CommentServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayName("Should execute test for CommentServiceImpl")
public class CommentServiceImplTest {
    private static final int EXPECTED_NUMBER_OF_COMMENTS = 1;

    @MockBean
    public AuthorRepository authorRepository;

    @MockBean
    @SpyBean
    public BookRepository bookRepository;

    @MockBean
    public CommentRepository commentRepository;

    @Autowired
    @InjectMocks
    public CommentServiceImpl commentService;


    @Test
    @DisplayName("should return comment by id")
    void shouldReturnCommentById() {
        // Arrange
        var author = new Author(1, "Author_1");
        var genre = new Genre(1, "Genre_1");
        long bookId = 1;
        var book = new Book(bookId, "Book_1", author, genre, List.of());
        long commentId = 1;
        var expectedComment = new Comment(commentId, "Comment_Text_1", book);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(expectedComment));

        // Act
        var comment = commentService.findById(bookId);

        // Assert
        assertThat(comment).isPresent().get().usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("should return comments by bookId")
    void shouldReturnCommentsByBookId() {
        // Arrange
        var author = new Author(1, "Author_1");
        var genre = new Genre(1, "Genre_1");
        long bookId = 1;
        var book = new Book(bookId, "Book_1", author, genre, List.of());
        var expectedComment = new Comment(1, "Comment_Text_1", book);
        var expectedCommentList = List.of(expectedComment);
        book.setComments(expectedCommentList);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        var commentList = commentService.findCommentsByBookId(bookId);

        // Assert
        assertThat(commentList).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(comment -> !comment.getText().isEmpty());
    }

    @Test
    @DisplayName("should insert comment")
    public void shouldInsertComment() {
        // Arrange
        var author = new Author(1, "Author_1");
        var genre = new Genre(1, "Genre_1");
        long bookId = 1;
        var book = new Book(bookId, "Book_1", author, genre, List.of());
        var commentText = "Comment_Text_1";
        var comment = new Comment(0, commentText, book);
        var expectedComment = new Comment(1, commentText, book);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(commentRepository.save(comment)).thenReturn(expectedComment);

        // Act
        var insertedComment = commentService.insert(commentText, bookId);

        // Assert
        assertThat(insertedComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }
@Test
@DisplayName("should update comment")
public void shouldUpdateComment() {
    // Arrange
    var author = new Author(1, "Author_1");
    var genre = new Genre(1, "Genre_1");
    long bookId = 1;
    var book = new Book(bookId, "Book_1", author, genre, List.of());
    long commentId = 1;
    var commentText = "Comment_Text_1";
    var comment = new Comment(commentId, commentText, book);
    Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
    Mockito.when(commentRepository.save(comment)).thenReturn(comment);

    // Act
    var updatedComment = commentService.update(commentId, commentText, bookId);

    // Assert
    assertThat(updatedComment).usingRecursiveComparison().isEqualTo(comment);
    verify(commentRepository, times(1)).save(comment);
}

    @Test
    @DisplayName("should delete comment")
    void shouldDeleteComment() {
        // Arrange
        var author = new Author(1, "Author_1");
        var genre = new Genre(1, "Genre_1");
        var book = new Book(1, "Book_1", author, genre, List.of());
        long commentId = 1;
        var commentText = "Comment_Text_1";
        var comment = new Comment(commentId, commentText, book);
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // Act
        commentService.deleteById(commentId);

        // Assert
        verify(commentRepository, times(1)).delete(comment);
    }
}
