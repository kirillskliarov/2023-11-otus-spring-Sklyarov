package ru.otus.hw.migration.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorData {
    private long _id;

    private String fullName;
}
