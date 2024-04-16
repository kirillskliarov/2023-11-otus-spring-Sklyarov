package ru.otus.hw.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDtoResponse {
    private long id;

    private String title;

    private Author author;

    private Genre genre;

    public BookDtoResponse(Book book) {
        id = book.getId();
        title = book.getTitle();
        author = book.getAuthor();
        genre = book.getGenre();
    }
}
