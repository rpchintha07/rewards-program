package com.example.rewards.controller;

import com.example.rewards.dto.TransactionsDTO;
import com.example.rewards.service.TransactionsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionsController implements TransactionsAPI {

    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @Override
    @GetMapping("/transactions/{customerId}")
    public ResponseEntity<List<TransactionsDTO>> getTransactionsByCustomerId(@Valid @PathVariable(value = "customerId")
                                                                             @NotNull(message = "customer id should not be null") Long customerId) {
        List<TransactionsDTO> transactions = transactionsService.getTransactionsByCustomerId(customerId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
