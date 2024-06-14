package ru.otus.project.budgeting.repositoties;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.project.budgeting.models.Income;

@Repository
public interface IncomeRepository extends ListCrudRepository<Income, Long>, JpaSpecificationExecutor<Income> {
}
