package ru.otus.project.budgeting.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.budgeting.models.Expense;
import ru.otus.project.budgeting.models.User;
import ru.otus.project.budgeting.models.dto.CreateExpenseResponse;
import ru.otus.project.budgeting.models.dto.GetExpenseListRequest;
import ru.otus.project.budgeting.models.dto.GetExpenseListResponse;
import ru.otus.project.budgeting.repositoties.ExpenseRepository;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Transactional
    @Override
    public CreateExpenseResponse create(Expense expense) {
        Expense createdIncome = expenseRepository.save(expense);
        return CreateExpenseResponse.builder()
                .id(createdIncome.getId())
                .build();
    }

    @Override
    public List<GetExpenseListResponse> findByQuery(User user, GetExpenseListRequest request) {
        return expenseRepository.findAll(
                        (Root<Expense> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
                            Specification<Expense> spec = Specification.where((r, q, cb) -> cb.equal(r.get("user"), user));

                            Long amountFrom = request.getAmountFrom();
                            if (amountFrom != null) {
                                spec = spec.and((r, q, cb) -> cb.greaterThanOrEqualTo(r.get("amount"), amountFrom));
                            }

                            Long amountTo = request.getAmountTo();
                            if (amountTo != null) {
                                spec = spec.and((r, q, cb) -> cb.lessThanOrEqualTo(r.get("amount"), amountTo));
                            }

                            Date startDate = request.getStartDate();
                            if (startDate != null) {
                                spec = spec.and((r, q, cb) -> cb.greaterThanOrEqualTo(r.get("date"), startDate));
                            }

                            Date endDate = request.getEndDate();
                            if (endDate != null) {
                                spec = spec.and((r, q, cb) -> cb.lessThanOrEqualTo(r.get("date"), endDate));
                            }

                            return spec.toPredicate(root, criteriaQuery, criteriaBuilder);
                        },
                        Sort.by(Sort.Order.desc("date"))
                )
                .stream()
                .map(
                        expense -> GetExpenseListResponse.builder()
                                .id(expense.getId())
                                .amount(expense.getAmount())
                                .date(expense.getDate())
                                .build()

                )
                .toList();
    }
}
