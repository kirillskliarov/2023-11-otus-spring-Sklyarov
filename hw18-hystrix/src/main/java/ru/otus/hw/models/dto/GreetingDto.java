package ru.otus.hw.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GreetingDto {
    private String greeting;

    private String lang;
}
