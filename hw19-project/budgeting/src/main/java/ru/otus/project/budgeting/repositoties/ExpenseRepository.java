package ru.otus.project.budgeting.repositoties;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.project.budgeting.models.Expense;
import ru.otus.project.budgeting.models.User;

import java.util.List;

@Repository
public interface ExpenseRepository extends ListCrudRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {
    List<Expense> findByUserOrderByDateDesc(User user);
}
