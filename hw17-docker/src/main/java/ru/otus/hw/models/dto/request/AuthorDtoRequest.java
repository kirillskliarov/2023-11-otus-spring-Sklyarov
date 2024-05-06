package ru.otus.hw.models.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDtoRequest {
    @NotNull
    @NotEmpty(message = "Should not be empty")
    private String fullName;
}
