package ru.otus.hw.legacy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "authors")
public class AuthorLegacy {
    @Transient
    public static final String SEQUENCE_NAME = "authors_sequence";

    @Id
    private long id;

    @NonNull
    private String fullName;
}
