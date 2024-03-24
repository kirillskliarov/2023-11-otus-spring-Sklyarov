package ru.otus.hw.repositories;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.hw.models.User;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
