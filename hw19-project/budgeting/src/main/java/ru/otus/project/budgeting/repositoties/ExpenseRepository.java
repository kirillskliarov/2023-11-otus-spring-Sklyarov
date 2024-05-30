package ru.otus.project.budgeting.repositoties;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.project.budgeting.models.Expense;
import ru.otus.project.budgeting.models.User;

import java.util.List;

@Repository
public interface ExpenseRepository extends ListCrudRepository<Expense, Long> {
    List<Expense> findByUserOrderByDateDesc(User user);
}
