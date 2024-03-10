package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.dto.request.AuthorDtoRequest;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/authors")
    public String getAll(
            AuthorDtoRequest authorDtoRequest,
            Model model) {
        List<Author> authors = authorService.findAll().stream().toList();
        model.addAttribute("authors", authors);
        return "authors";
    }


    @PostMapping("/authors")
    public String create(
            @Valid @ModelAttribute AuthorDtoRequest authorDtoRequest,
            BindingResult bindingResult,
            Model model) {
        List<Author> authors = authorService.findAll().stream().toList();
        model.addAttribute("authors", authors);
        if (bindingResult.hasErrors()) {
            return "authors";
        }

        authorService.insert(authorDtoRequest.getFullName());
        return "redirect:/authors";
    }
}
