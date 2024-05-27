package ru.otus.project.budgeting.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.budgeting.models.Income;
import ru.otus.project.budgeting.models.User;
import ru.otus.project.budgeting.models.dto.CreateIncomeRequest;
import ru.otus.project.budgeting.models.dto.CreateIncomeResponse;
import ru.otus.project.budgeting.services.IncomeService;

@RestController
@RequiredArgsConstructor
public class IncomeController {
    private final IncomeService incomeService;

    @PostMapping("/income")
    public CreateIncomeResponse createIncome(
            @RequestBody CreateIncomeRequest createIncomeRequest,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Income income = Income.builder()
                .amount(createIncomeRequest.getAmount())
                .user(user)
                .build();

        Income createdIncome = incomeService.create(income);
        long id = createdIncome.getId();

        return CreateIncomeResponse.builder()
                .id(id)
                .build();
    }
}
