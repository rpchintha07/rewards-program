package com.example.rewards.service;

import com.example.rewards.dto.RewardSummaryDTO;
import com.example.rewards.dto.RewardSummaryDTO.MonthlyReward;
import com.example.rewards.entity.Transaction;
import com.example.rewards.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class RewardService {

    private final TransactionRepository transactionRepository;

    public RewardService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<RewardSummaryDTO> getAllRewards() {
        Map<Long, List<Transaction>> byCustomer = new HashMap<>();
        for (Transaction t : transactionRepository.findAll()) {
            byCustomer.computeIfAbsent(t.getCustomerId(), k -> new ArrayList<>()).add(t);
        }
        List<RewardSummaryDTO> result = new ArrayList<>();
        for (Map.Entry<Long, List<Transaction>> entry : byCustomer.entrySet()) {
            result.add(calculateRewardSummary(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    public RewardSummaryDTO getRewardsForCustomer(Long customerId) {
        List<Transaction> tx = transactionRepository.findByCustomerId(customerId);
        return calculateRewardSummary(customerId, tx);
    }

    private RewardSummaryDTO calculateRewardSummary(Long customerId, List<Transaction> transactions) {
        Map<String, Integer> monthly = new HashMap<>();
        int total = 0;
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");

        for (Transaction t : transactions) {
            if (t.getDate().isBefore(threeMonthsAgo)) continue;
            int points = calculatePoints(t.getAmount());
            String month = t.getDate().format(fmt);
            monthly.put(month, monthly.getOrDefault(month, 0) + points);
            total += points;
        }

        List<MonthlyReward> monthlyRewards = new ArrayList<>();
        for (Map.Entry<String, Integer> e : monthly.entrySet()) {
            monthlyRewards.add(new MonthlyReward(e.getKey(), e.getValue()));
        }
        monthlyRewards.sort(Comparator.comparing(MonthlyReward::getMonth));
        return new RewardSummaryDTO(customerId, total, monthlyRewards);
    }

    private int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (int) ((amount - 100) * 2 + 50);
        } else if (amount > 50) {
            points += (int) (amount - 50);
        }
        return points;
    }
}
