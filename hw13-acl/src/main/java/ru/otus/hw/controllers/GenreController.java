package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.models.Genre;
import ru.otus.hw.models.dto.request.GenreDtoRequest;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class GenreController {
    private final GenreService genreService;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/genres")
    public String getAll(GenreDtoRequest genreDtoRequest, Model model) {
        List<Genre> genres = genreService.findAll().stream().toList();
        model.addAttribute("genres", genres);
        return "genres";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/genres")
    public String create(
            @Valid @ModelAttribute GenreDtoRequest genreDtoRequest,
            BindingResult bindingResult,
            Model model
            ) {
        List<Genre> genres = genreService.findAll().stream().toList();
        model.addAttribute("genres", genres);
        if (bindingResult.hasErrors()) {
            return "genres";
        }

        genreService.insert(genreDtoRequest.getName());
        return "redirect:/genres";

    }
}
