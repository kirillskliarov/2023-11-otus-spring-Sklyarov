package ru.otus.hw.migration.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookData {
    private long _id;

    private String title;

    private long author_id;

    private long genre_id;
}
