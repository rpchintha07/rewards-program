package com.example.rewards.service;

import com.example.rewards.dto.RewardSummaryDTO;
import com.example.rewards.entity.Transaction;
import com.example.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RewardServiceTest {

    private RewardService rewardService;
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        rewardService = new RewardService(transactionRepository);
    }

    @Test
    void testSingleCustomer_MultipleTransactions() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1L, 1L, 120, LocalDate.now().minusMonths(1)),
                new Transaction(2L, 1L, 80, LocalDate.now().minusMonths(2)),
                new Transaction(3L, 1L, 200, LocalDate.now().minusMonths(3))
        );
        Mockito.when(transactionRepository.findByCustomerId(1L)).thenReturn(transactions);
        RewardSummaryDTO summary = rewardService.getRewardsForCustomer(1L);

        assertEquals(3, summary.getMonthlyRewards().size());
        assertTrue(summary.getTotalPoints() > 0);
    }

    @Test
    void testMultipleCustomers() {
        List<Transaction> allTx = Arrays.asList(
                new Transaction(1L, 1L, 120, LocalDate.now().minusMonths(1)),
                new Transaction(2L, 2L, 80, LocalDate.now().minusMonths(1)),
                new Transaction(3L, 2L, 150, LocalDate.now().minusMonths(2))
        );
        Mockito.when(transactionRepository.findAll()).thenReturn(allTx);

        List<RewardSummaryDTO> summaries = rewardService.getAllRewards();
        assertEquals(2, summaries.size());
        int totalPoints = summaries.stream().mapToInt(RewardSummaryDTO::getTotalPoints).sum();
        assertTrue(totalPoints > 0);
    }

    @Test
    void testNoTransactions() {
        Mockito.when(transactionRepository.findByCustomerId(99L)).thenReturn(Collections.emptyList());
        RewardSummaryDTO summary = rewardService.getRewardsForCustomer(99L);

        assertEquals(0, summary.getTotalPoints());
        assertTrue(summary.getMonthlyRewards().isEmpty());
    }

    @Test
    void testOnlyTransactionsBelowFifty() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1L, 1L, 40, LocalDate.now().minusMonths(1)),
                new Transaction(2L, 1L, 20, LocalDate.now().minusMonths(2))
        );
        Mockito.when(transactionRepository.findByCustomerId(1L)).thenReturn(transactions);
        RewardSummaryDTO summary = rewardService.getRewardsForCustomer(1L);

        assertEquals(0, summary.getTotalPoints());
        assertEquals(2, summary.getMonthlyRewards().size());
    }

    @Test
    void testAllTransactionsOverHundred() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1L, 1L, 150, LocalDate.now().minusMonths(1)),
                new Transaction(2L, 1L, 200, LocalDate.now().minusMonths(1))
        );
        Mockito.when(transactionRepository.findByCustomerId(1L)).thenReturn(transactions);
        RewardSummaryDTO summary = rewardService.getRewardsForCustomer(1L);

        assertEquals(1, summary.getMonthlyRewards().size());
        int expected = ((int) ((150-100)*2 + 50)) + ((int) ((200-100)*2 + 50));
        assertEquals(expected, summary.getTotalPoints());
    }

    @Test
    void testTransactionExactlyAtFiftyAndHundred() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1L, 1L, 50, LocalDate.now().minusMonths(1)),
                new Transaction(2L, 1L, 100, LocalDate.now().minusMonths(1))
        );
        Mockito.when(transactionRepository.findByCustomerId(1L)).thenReturn(transactions);
        RewardSummaryDTO summary = rewardService.getRewardsForCustomer(1L);

        // Both should contribute 0 points
        assertEquals(0, summary.getTotalPoints());
        assertEquals(1, summary.getMonthlyRewards().size());
        assertEquals(0, summary.getMonthlyRewards().get(0).getPoints());
    }
}
