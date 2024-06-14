package ru.otus.project.budgeting.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.budgeting.models.Expense;
import ru.otus.project.budgeting.models.ExpenseCategory;
import ru.otus.project.budgeting.models.User;
import ru.otus.project.budgeting.models.dto.CreateExpenseRequest;
import ru.otus.project.budgeting.models.dto.CreateExpenseResponse;
import ru.otus.project.budgeting.models.dto.GetExpenseCategoryListResponse;
import ru.otus.project.budgeting.models.dto.GetExpenseListRequest;
import ru.otus.project.budgeting.models.dto.GetExpenseListResponse;
import ru.otus.project.budgeting.repositoties.ExpenseCategoryRepository;
import ru.otus.project.budgeting.repositoties.ExpenseRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Transactional
    @Override
    public CreateExpenseResponse create(User user, CreateExpenseRequest createExpenseRequest) {
        ExpenseCategory expenseCategory = expenseCategoryRepository.findByUserAndId(user, createExpenseRequest.getCategoryId()).orElseThrow(EntityNotFoundException::new);
        Expense expense = Expense.builder()
                .amount(createExpenseRequest.getAmount())
                .description(createExpenseRequest.getDescription())
                .date(createExpenseRequest.getDate())
                .user(user)
                .expenseCategory(expenseCategory)
                .build();
        Expense createdExpense = expenseRepository.save(expense);
        return CreateExpenseResponse.builder()
                .id(createdExpense.getId())
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

                            String description = request.getDescription();
                            if (description != null) {
                                spec = spec.and((r, q, cb) -> cb.like(r.get("description"), description));
                            }

                            Date startDate = request.getStartDate();
                            if (startDate != null) {
                                spec = spec.and((r, q, cb) -> cb.greaterThanOrEqualTo(r.get("date"), startDate));
                            }

                            Date endDate = request.getEndDate();
                            if (endDate != null) {
                                spec = spec.and((r, q, cb) -> cb.lessThanOrEqualTo(r.get("date"), endDate));
                            }
                            Long categoryId = request.getCategoryId();
                            if (categoryId != null) {
                                Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findById(categoryId);
                                if (expenseCategory.isPresent()) {
                                    spec = spec.and((r, q, cb) -> cb.equal(r.get("expenseCategory"), expenseCategory.get()));
                                }
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
                                .description(expense.getDescription())
                                .date(expense.getDate())
                                .expenseCategory(GetExpenseCategoryListResponse.builder()
                                        .id(expense.getExpenseCategory().getId())
                                        .description(expense.getExpenseCategory().getDescription())
                                        .build())
                                .build()

                )
                .toList();
    }
}
