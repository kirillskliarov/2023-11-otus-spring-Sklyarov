package ru.otus.project.budgeting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetExpenseListResponse {
    private Long id;
    private Long amount;
    private String description;
    private Date date;
    private GetExpenseCategoryListResponse expenseCategory;
}
