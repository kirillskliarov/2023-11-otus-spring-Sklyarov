package ru.otus.project.budgeting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetExpenseCategoryListResponse {
    private Long id;
    private String description;
}
