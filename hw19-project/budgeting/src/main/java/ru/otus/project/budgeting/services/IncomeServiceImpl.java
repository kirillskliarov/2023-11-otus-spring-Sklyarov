package ru.otus.project.budgeting.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.budgeting.models.Income;
import ru.otus.project.budgeting.repositoties.IncomeRepository;

@RequiredArgsConstructor
@Service
public class IncomeServiceImpl implements IncomeService {
    private final IncomeRepository incomeRepository;

    @Transactional
    public Income create(Income income) {
        return incomeRepository.save(income);
    }
}
