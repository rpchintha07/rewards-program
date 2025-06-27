package com.example.rewards.service;

import com.example.rewards.dto.TransactionsDTO;
import com.example.rewards.entity.Transaction;
import com.example.rewards.exception.TransactionNotFoundException;
import com.example.rewards.repository.TransactionRepository;
import com.example.rewards.util.TransactionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionsService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionsService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public List<TransactionsDTO> getTransactionsByCustomerId(Long customerId) {
        List<Transaction> transactions = Optional.ofNullable(transactionRepository.findByCustomerId(customerId))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new TransactionNotFoundException("Transactions not found for the given customer id: " + customerId));

        return transactionMapper.toDTOList(transactions);
    }

}
