package ru.otus.project.budgeting.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.budgeting.models.Income;
import ru.otus.project.budgeting.models.User;
import ru.otus.project.budgeting.models.dto.CreateIncomeResponse;
import ru.otus.project.budgeting.models.dto.GetIncomeListResponse;
import ru.otus.project.budgeting.repositoties.IncomeRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IncomeServiceImpl implements IncomeService {
    private final IncomeRepository incomeRepository;

    @Transactional
    public CreateIncomeResponse create(Income income) {
        Income createdIncome = incomeRepository.save(income);
        return CreateIncomeResponse.builder()
                .id(createdIncome.getId())
                .build();
    }

    public List<GetIncomeListResponse> findByUser(User user) {
        List<Income> incomeList = incomeRepository.findByUser(user);

        return incomeList.stream().map(income -> GetIncomeListResponse.builder()
                .id(income.getId())
                .amount(income.getAmount())
                .build()
        ).toList();
    }

}
