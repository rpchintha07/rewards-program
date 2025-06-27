package com.example.rewards.service;

import com.example.rewards.dto.CustomerRewardsDTO;
import com.example.rewards.dto.CustomerRewardsDTO.MonthlyReward;
import com.example.rewards.entity.Transaction;
import com.example.rewards.exception.TransactionNotFoundException;
import com.example.rewards.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardsService {

    private final TransactionRepository transactionRepository;

    public RewardsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<CustomerRewardsDTO> getAllRewards() {

        List<Transaction> transactions = transactionRepository.findAll();
        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException("No transactions found");
        }

        return transactions.stream()
                    .collect(Collectors.groupingBy(Transaction::getCustomerId))
                    .entrySet()
                    .stream()
                    .map(entry -> calculateRewardSummary(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
    }

    public CustomerRewardsDTO getRewardsByCustomerId(Long customerId) {
        List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);

        if (transactions == null || transactions.isEmpty()) {
            throw new TransactionNotFoundException("No transactions found for customer ID: " + customerId);
        }

        return calculateRewardSummary(customerId, transactions);
    }


    public CustomerRewardsDTO calculateRewardSummary(Long customerId, List<Transaction> transactions) {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");

        Map<String, Integer> monthly = transactions.stream()
                .filter(t -> !t.getTransactionDate().isBefore(threeMonthsAgo))
                .collect(Collectors.groupingBy(
                        t -> t.getTransactionDate().format(fmt),
                        Collectors.summingInt(t -> calculatePoints(t.getTransactionAmount()))
                ));

        int total = monthly.values().stream().mapToInt(Integer::intValue).sum();

        List<MonthlyReward> monthlyRewards = monthly.entrySet().stream()
                .map(e -> new MonthlyReward(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(MonthlyReward::getMonth))
                .collect(Collectors.toList());

        return new CustomerRewardsDTO(customerId, total, monthlyRewards);
    }


    private int calculatePoints(Double amount) {
        int points = 0;
        if (amount > 100) {
            points += (int) ((amount - 100) * 2 + 50);
        } else if (amount > 50) {
            points += (int) (amount - 50);
        }
        return points;
    }
}
