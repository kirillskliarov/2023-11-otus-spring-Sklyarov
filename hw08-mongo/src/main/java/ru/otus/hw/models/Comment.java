package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Transient
    public static final String SEQUENCE_NAME = "comments_sequence";

    private long id;

    private String text;

}
