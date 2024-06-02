package ru.otus.project.budgeting.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.budgeting.models.ExpenseCategory;
import ru.otus.project.budgeting.models.User;
import ru.otus.project.budgeting.models.dto.CreateExpenseCategoryRequest;
import ru.otus.project.budgeting.models.dto.CreateExpenseCategoryResponse;
import ru.otus.project.budgeting.models.dto.GetExpenseCategoryListResponse;
import ru.otus.project.budgeting.services.ExpenseCategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExpenseCategoryController {
    private final ExpenseCategoryService expenseCategoryService;

    @PostMapping("/api/expense-category")
    public CreateExpenseCategoryResponse create(
            @RequestBody CreateExpenseCategoryRequest createExpenseCategoryRequest,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        ExpenseCategory expenseCategory = ExpenseCategory.builder()
                .description(createExpenseCategoryRequest.getDescription())
                .user(user)
                .build();

        return expenseCategoryService.create(expenseCategory);
    }

    @GetMapping("/api/expense-category")
    public List<GetExpenseCategoryListResponse> getList(
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return expenseCategoryService.findByUser(user);
    }
}
