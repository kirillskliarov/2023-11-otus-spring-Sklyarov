package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.services.AuthorService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    // aa
    @ShellMethod(value = "Find all authors", key = "aa")
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // aid 1
    @ShellMethod(value = "Find author by id", key = "aid")
    public String findAuthorById(@ShellOption long id) {
        return authorService.findById(id).stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // ains newAuthor
    @ShellMethod(value = "Insert author", key = "ains")
    public String insertAuthor(@ShellOption String fullName) {
        var savedAuthor = authorService.insert(fullName);
        return authorConverter.authorToString(savedAuthor);
    }
}
