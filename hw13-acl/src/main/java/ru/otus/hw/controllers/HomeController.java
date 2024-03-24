package ru.otus.hw.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/")
    public RedirectView redirectFromMainPage() {
        return new RedirectView("/genres");
    }
}
