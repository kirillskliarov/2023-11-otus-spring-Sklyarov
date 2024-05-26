package ru.otus.project.budgeting.repositoties;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.project.budgeting.models.Income;
import ru.otus.project.budgeting.models.User;

import java.util.List;

@Repository
public interface IncomeRepository extends ListCrudRepository<Income, Long> {
    List<Income> findByUser(User user);
}
