package ru.otus.project.budgeting.services;

import ru.otus.project.budgeting.models.ExpenseCategory;
import ru.otus.project.budgeting.models.User;
import ru.otus.project.budgeting.models.dto.CreateExpenseCategoryResponse;
import ru.otus.project.budgeting.models.dto.CreateExpenseResponse;
import ru.otus.project.budgeting.models.dto.GetExpenseCategoryListResponse;

import java.util.List;

public interface ExpenseCategoryService {
    CreateExpenseCategoryResponse create(ExpenseCategory expenseCategory);

    List<GetExpenseCategoryListResponse> findByUser(User user);
}
