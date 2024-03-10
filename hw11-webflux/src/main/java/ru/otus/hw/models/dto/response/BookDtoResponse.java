package ru.otus.hw.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDtoResponse {
    private long id;

    private String title;

    private Author author;

    private Genre genre;

    private List<Comment> comments;

    public BookDtoResponse(Book book) {
        id = book.getId();
        title = book.getTitle();
        comments = book.getComments();
//        author = book.getAuthor();
//        genre = book.getGenre();
    }
}
