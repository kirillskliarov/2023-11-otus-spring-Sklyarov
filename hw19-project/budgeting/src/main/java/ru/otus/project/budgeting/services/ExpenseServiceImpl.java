package ru.otus.project.budgeting.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.budgeting.models.Expense;
import ru.otus.project.budgeting.models.User;
import ru.otus.project.budgeting.models.dto.CreateExpenseResponse;
import ru.otus.project.budgeting.models.dto.GetExpenseListResponse;
import ru.otus.project.budgeting.repositoties.ExpenseRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Transactional
    public CreateExpenseResponse create(Expense expense) {
        Expense createdIncome = expenseRepository.save(expense);
        return CreateExpenseResponse.builder()
                .id(createdIncome.getId())
                .build();
    }

    public List<GetExpenseListResponse> findByUser(User user) {
        List<Expense> expenseList = expenseRepository.findByUserOrderByDateDesc(user);

        return expenseList.stream().map(income -> GetExpenseListResponse.builder()
                .id(income.getId())
                .amount(income.getAmount())
                .date(income.getDate())
                .build()
        ).toList();
    }
}
