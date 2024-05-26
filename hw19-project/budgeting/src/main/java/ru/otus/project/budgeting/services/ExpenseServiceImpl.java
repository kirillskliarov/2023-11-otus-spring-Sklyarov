package ru.otus.project.budgeting.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.budgeting.models.Expense;
import ru.otus.project.budgeting.repositoties.ExpenseRepository;

@RequiredArgsConstructor
@Service
public class ExpenseServiceImpl {
    private final ExpenseRepository expenseRepository;

    @Transactional
    public Expense create(Expense expense) {
        return expenseRepository.save(expense);
    }
}
