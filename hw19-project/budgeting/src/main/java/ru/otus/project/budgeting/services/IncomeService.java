package ru.otus.project.budgeting.services;

import ru.otus.project.budgeting.models.Income;
import ru.otus.project.budgeting.models.User;
import ru.otus.project.budgeting.models.dto.CreateIncomeResponse;
import ru.otus.project.budgeting.models.dto.GetExpenseListRequest;
import ru.otus.project.budgeting.models.dto.GetExpenseListResponse;
import ru.otus.project.budgeting.models.dto.GetIncomeListRequest;
import ru.otus.project.budgeting.models.dto.GetIncomeListResponse;

import java.util.List;

public interface IncomeService {
    CreateIncomeResponse create(Income income);

    List<GetIncomeListResponse> findByQuery(User user, GetIncomeListRequest request);
}
