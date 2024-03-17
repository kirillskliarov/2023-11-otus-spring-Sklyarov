package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.dto.request.BookDtoRequest;
import ru.otus.hw.models.dto.request.CommentDtoRequest;
import ru.otus.hw.models.dto.response.BookDtoResponse;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class BookController {
    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/books")
    public String getAll(BookDtoRequest bookDtoRequest, Model model) {
        List<BookDtoResponse> books = bookService.findAll().stream().toList();
        List<Genre> genres = genreService.findAll().stream().toList();
        List<Author> authors = authorService.findAll().stream().toList();
        model.addAttribute("books", books)
                .addAttribute("genres", genres)
                .addAttribute("authors", authors);
        return "books";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/books")
    public String create(
            @Valid @ModelAttribute BookDtoRequest bookDtoRequest,
            BindingResult bindingResult,
            Model model) {
        List<BookDtoResponse> books = bookService.findAll().stream().toList();
        List<Genre> genres = genreService.findAll().stream().toList();
        List<Author> authors = authorService.findAll().stream().toList();
        model.addAttribute("books", books)
                .addAttribute("genres", genres)
                .addAttribute("authors", authors);

        if (bindingResult.hasErrors()) {
            return "books";
        }

        bookService.insert(bookDtoRequest.getTitle(), bookDtoRequest.getAuthorId(), bookDtoRequest.getGenreId());
        return "redirect:/books";
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/books/{id}")
    public String getById(
            @PathVariable long id,
            Model model
    ) {
        Optional<Book> book = bookService.findById(id);
        model.addAttribute("book", book.get());
        return "book";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/books/{id}/edit")
    public String getEditFormBookById(
            @PathVariable long id,
            BookDtoRequest bookDtoRequest,
            Model model
    ) {
        Optional<Book> book = bookService.findById(id);
        List<Genre> genres = genreService.findAll().stream().toList();
        List<Author> authors = authorService.findAll().stream().toList();
        model.addAttribute("book", book.get())
                .addAttribute("genres", genres)
                .addAttribute("authors", authors);
        return "book-edit";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/books/{id}/edit")
    public String update(
            @PathVariable long id,
            @Valid @ModelAttribute BookDtoRequest bookDtoRequest,
            BindingResult bindingResult,
            Model model
    ) {
        Book book = bookService
                .update(id, bookDtoRequest.getTitle(), bookDtoRequest.getAuthorId(), bookDtoRequest.getGenreId());
        List<Genre> genres = genreService.findAll().stream().toList();
        List<Author> authors = authorService.findAll().stream().toList();
        model.addAttribute("book", book)
                .addAttribute("genres", genres)
                .addAttribute("authors", authors);

        if (bindingResult.hasErrors()) {
            return "book-edit";
        }

        return "redirect:/books/" + book.getId();

    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/books/{id}/comment")
    public RedirectView createComment(@PathVariable long id, @ModelAttribute CommentDtoRequest commentDtoRequest) {
        commentService.insert(commentDtoRequest.getText(), id);
        return new RedirectView("/books/" + id);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/books/{id}/delete")
    public RedirectView delete(@PathVariable long id) {
        bookService.deleteById(id);
        return new RedirectView("/books");
    }
}
