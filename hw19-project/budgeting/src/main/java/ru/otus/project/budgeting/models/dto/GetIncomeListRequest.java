package ru.otus.project.budgeting.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GetIncomeListRequest {
    private Long amountFrom;
    private Long amountTo;
    private String description;
    private Date startDate;
    private Date endDate;
}
