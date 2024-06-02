package ru.otus.project.budgeting.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.budgeting.models.Income;
import ru.otus.project.budgeting.models.User;
import ru.otus.project.budgeting.models.dto.CreateIncomeResponse;
import ru.otus.project.budgeting.models.dto.GetIncomeListRequest;
import ru.otus.project.budgeting.models.dto.GetIncomeListResponse;
import ru.otus.project.budgeting.repositoties.IncomeRepository;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IncomeServiceImpl implements IncomeService {
    private final IncomeRepository incomeRepository;

    @Transactional
    @Override
    public CreateIncomeResponse create(Income income) {
        Income createdIncome = incomeRepository.save(income);
        return CreateIncomeResponse.builder()
                .id(createdIncome.getId())
                .build();
    }

    @Override
    public List<GetIncomeListResponse> findByQuery(User user, GetIncomeListRequest request) {
        return incomeRepository.findAll(
                        (Root<Income> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
                            Specification<Income> spec = Specification.where((r, q, cb) -> cb.equal(r.get("user"), user));

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
                        income -> GetIncomeListResponse.builder()
                                .id(income.getId())
                                .amount(income.getAmount())
                                .date(income.getDate())
                                .build()

                )
                .toList();
    }
}
