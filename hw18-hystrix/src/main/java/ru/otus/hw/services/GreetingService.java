package ru.otus.hw.services;

import ru.otus.hw.models.dto.GreetingDto;

public interface GreetingService {
    GreetingDto getGreeting(Long id) throws Exception;
}
