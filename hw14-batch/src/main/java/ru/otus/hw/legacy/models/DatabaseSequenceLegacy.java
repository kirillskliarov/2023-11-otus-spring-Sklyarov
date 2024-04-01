package ru.otus.hw.legacy.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
@AllArgsConstructor
public class DatabaseSequenceLegacy {
    @Id
    private String id;

    @Getter
    @Setter
    private long sequence;

    public void increment() {
        sequence++;
    }
}
