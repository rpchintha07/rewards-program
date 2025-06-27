package com.example.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsDTO {

    private Long id;
    private Long customerId;
    private Double transactionAmount;
    private LocalDate transactionDate;
}
