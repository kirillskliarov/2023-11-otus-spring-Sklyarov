package ru.otus.project.budgeting.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CreateExpenseRequest {
    private Long amount;
    private Date date;
}
