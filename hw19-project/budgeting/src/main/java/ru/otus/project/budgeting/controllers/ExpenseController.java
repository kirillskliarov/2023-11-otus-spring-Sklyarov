package ru.otus.project.budgeting.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.budgeting.models.Expense;
import ru.otus.project.budgeting.models.User;
import ru.otus.project.budgeting.models.dto.CreateExpenseRequest;
import ru.otus.project.budgeting.models.dto.CreateExpenseResponse;
import ru.otus.project.budgeting.models.dto.GetExpenseListResponse;
import ru.otus.project.budgeting.services.ExpenseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping("/api/expense")
    public CreateExpenseResponse create(
            @RequestBody CreateExpenseRequest createExpenseRequest,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Expense expense = Expense.builder()
                .amount(createExpenseRequest.getAmount())
                .date(createExpenseRequest.getDate())
                .user(user)
                .build();

        return expenseService.create(expense);
    }

    @GetMapping("/api/expense")
    public List<GetExpenseListResponse> getList(
//            @RequestBody CreateExpenseRequest createExpenseRequest,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return expenseService.findByUser(user);
    }
}
