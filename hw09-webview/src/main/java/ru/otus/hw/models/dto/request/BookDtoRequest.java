package ru.otus.hw.models.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDtoRequest {
//    private long id;

    @NotNull
    @NotEmpty(message = "Should not be empty")
    private String title;

    @NotNull(message = "Should not be empty")
    private Long authorId;

    @NotNull(message = "Should not be empty")
    private Long genreId;
}
