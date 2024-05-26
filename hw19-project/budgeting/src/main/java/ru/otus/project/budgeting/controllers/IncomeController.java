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

@RestController
@RequiredArgsConstructor
public class IncomeController {
    @PostMapping("/income")
    public CreateIncomeResponse createIncome(
            @RequestBody CreateIncomeRequest createIncomeRequest,
            Authentication authentication
        ) {
        User user = (User)authentication.getPrincipal();
        Income income = Income.builder()
                .amount(createIncomeRequest.getAmount())
                .user(user)
                .build();
        return new CreateIncomeResponse();
    }
}
