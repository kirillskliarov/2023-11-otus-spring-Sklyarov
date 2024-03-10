package ru.otus.hw.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDtoRequest {
    private long id;

    private String title;

    private long authorId;

    private long genreId;
}
