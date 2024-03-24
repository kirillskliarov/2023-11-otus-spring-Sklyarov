package ru.otus.hw.legacy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class BookLegacy {
    @Transient
    public static final String SEQUENCE_NAME = "books_sequence";

    @Id
    private long id;

    private String title;

    @DBRef
    private AuthorLegacy author;

    @DBRef
    private GenreLegacy genre;

}
