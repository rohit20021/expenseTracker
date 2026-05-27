package com.project.expenseTracker.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TransactionInputDto {
    private String userId; // Assuming you pass user ID
    private String categoryName; // Name of the category
    private BigDecimal amount;
    private String description;
}
