package ru.otus.hw.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
@AllArgsConstructor
public class DatabaseSequence {
    @Id
    private String id;

    @Getter
    @Setter
    private Long sequence;

    public void increment() {
        sequence++;
    }
}
