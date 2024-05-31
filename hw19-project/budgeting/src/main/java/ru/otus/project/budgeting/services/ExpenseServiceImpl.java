package ru.otus.project.budgeting.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.budgeting.models.Expense;
import ru.otus.project.budgeting.models.User;
import ru.otus.project.budgeting.models.dto.CreateExpenseResponse;
import ru.otus.project.budgeting.models.dto.GetExpenseListRequest;
import ru.otus.project.budgeting.models.dto.GetExpenseListResponse;
import ru.otus.project.budgeting.repositoties.ExpenseRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final EntityManager entityManager;

    @Transactional
    @Override
    public CreateExpenseResponse create(Expense expense) {
        Expense createdIncome = expenseRepository.save(expense);
        return CreateExpenseResponse.builder()
                .id(createdIncome.getId())
                .build();
    }

    @Override
    public List<GetExpenseListResponse> findByUser(User user) {
        List<Expense> expenseList = expenseRepository.findByUserOrderByDateDesc(user);

        return expenseList.stream().map(income -> GetExpenseListResponse.builder()
                .id(income.getId())
                .amount(income.getAmount())
                .date(income.getDate())
                .build()
        ).toList();
    }


    @Override
    public List<GetExpenseListResponse> findByQuery(User user, GetExpenseListRequest request) {
        return expenseRepository.findAll(
                        (Root<Expense> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
                            Specification<Expense> spec = Specification.where(null);

                            Long amountFrom = request.getAmountFrom();
                            if (amountFrom != null) {
                                spec = spec.and((r, q, cb) -> cb.greaterThanOrEqualTo(r.get("amount"), amountFrom));
                            }
                            return spec.toPredicate(root, criteriaQuery, criteriaBuilder);
                        }
                ).stream()
                .map(
                        income -> GetExpenseListResponse.builder()
                                .id(income.getId())
                                .amount(income.getAmount())
                                .date(income.getDate())
                                .build()

                ).toList();
    }
}
