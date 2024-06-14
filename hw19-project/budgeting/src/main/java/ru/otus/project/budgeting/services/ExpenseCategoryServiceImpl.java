package ru.otus.project.budgeting.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.project.budgeting.models.ExpenseCategory;
import ru.otus.project.budgeting.models.User;
import ru.otus.project.budgeting.models.dto.CreateExpenseCategoryResponse;
import ru.otus.project.budgeting.models.dto.GetExpenseCategoryListResponse;
import ru.otus.project.budgeting.repositoties.ExpenseCategoryRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public CreateExpenseCategoryResponse create(ExpenseCategory expenseCategory) {
        ExpenseCategory createdExpenseCategory = expenseCategoryRepository.save(expenseCategory);
        return CreateExpenseCategoryResponse.builder()
                .id(createdExpenseCategory.getId())
                .build();
    }

    public List<GetExpenseCategoryListResponse> findByUser(User user) {
        return expenseCategoryRepository.findByUser(user)
                .stream()
                .map(expenseCategory -> GetExpenseCategoryListResponse.builder()
                        .id(expenseCategory.getId())
                        .description(expenseCategory.getDescription())
                        .build())
                .toList();
    }
}
