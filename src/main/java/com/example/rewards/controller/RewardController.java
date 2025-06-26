package com.example.rewards.controller;

import com.example.rewards.dto.RewardSummaryDTO;
import com.example.rewards.entity.Transaction;
import com.example.rewards.service.RewardService;
import com.example.rewards.repository.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class RewardController {

    private final RewardService rewardService;
    private final TransactionRepository transactionRepository;

    public RewardController(RewardService rewardService, TransactionRepository transactionRepository) {
        this.rewardService = rewardService;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/rewards")
    public List<RewardSummaryDTO> getAllRewards() {
        return rewardService.getAllRewards();
    }

    @GetMapping("/rewards/{customerId}")
    public RewardSummaryDTO getRewardsForCustomer(@PathVariable Long customerId) {
        return rewardService.getRewardsForCustomer(customerId);
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
