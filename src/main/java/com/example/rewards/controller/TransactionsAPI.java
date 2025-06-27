package com.example.rewards.controller;

import com.example.rewards.dto.TransactionsDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Validated
public interface TransactionsAPI {

    ResponseEntity<List<TransactionsDTO>> getTransactionsByCustomerId(@Valid @PathVariable(value = "customerId")
                                                                      @NotNull(message = "customer id should not be null") Long customerId);
}
