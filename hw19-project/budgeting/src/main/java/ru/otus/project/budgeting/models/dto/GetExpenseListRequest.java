package ru.otus.project.budgeting.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GetExpenseListRequest {
    private Long amountFrom;
    private Long amountTo;
    private Date startDate;
    private Date endDate;
}
