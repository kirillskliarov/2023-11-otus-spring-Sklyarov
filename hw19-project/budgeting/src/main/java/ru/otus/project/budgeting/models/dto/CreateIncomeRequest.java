package ru.otus.project.budgeting.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CreateIncomeRequest {
    private Long amount;
    private Date date;
}
