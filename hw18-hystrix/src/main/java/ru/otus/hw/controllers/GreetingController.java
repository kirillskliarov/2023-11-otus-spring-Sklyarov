package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.models.dto.GreetingDto;
import ru.otus.hw.services.GreetingService;

@RequiredArgsConstructor
@RestController
public class GreetingController {
    private final GreetingService greetingService;


    @GetMapping("/api/greeting/{id}")
    public GreetingDto getGreeting(@PathVariable Long id) throws Exception {
        return greetingService.getGreeting(id);
    }
}
