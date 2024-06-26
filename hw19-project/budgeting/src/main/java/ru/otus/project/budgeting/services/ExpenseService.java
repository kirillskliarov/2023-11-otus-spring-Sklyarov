package ru.otus.project.budgeting.services;

import ru.otus.project.budgeting.models.Expense;
import ru.otus.project.budgeting.models.User;
import ru.otus.project.budgeting.models.dto.CreateExpenseRequest;
import ru.otus.project.budgeting.models.dto.CreateExpenseResponse;
import ru.otus.project.budgeting.models.dto.GetExpenseListRequest;
import ru.otus.project.budgeting.models.dto.GetExpenseListResponse;

import java.util.List;

public interface ExpenseService {
    CreateExpenseResponse create(User user, CreateExpenseRequest expense);

    List<GetExpenseListResponse> findByQuery(User user, GetExpenseListRequest request);
}
