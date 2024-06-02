package ru.otus.project.budgeting.repositoties;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.project.budgeting.models.ExpenseCategory;


@Repository
public interface ExpenseCategoryRepository extends ListCrudRepository<ExpenseCategory, Long>, JpaSpecificationExecutor<ExpenseCategory> {
}
