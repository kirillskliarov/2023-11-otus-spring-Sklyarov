package ru.otus.project.budgeting.repositoties;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.project.budgeting.models.ExpenseCategory;
import ru.otus.project.budgeting.models.User;

import java.util.List;


@Repository
public interface ExpenseCategoryRepository extends ListCrudRepository<ExpenseCategory, Long>, JpaSpecificationExecutor<ExpenseCategory> {
    List<ExpenseCategory> findByUser(User user);
}
