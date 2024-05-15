package ru.otus.hw.repositories;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.hw.models.Greeting;

public interface GreetingRepository extends ListCrudRepository<Greeting, Long> {
}
