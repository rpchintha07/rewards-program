package com.example.rewards.service;

import com.example.rewards.dto.CustomerRewardsDTO;
import com.example.rewards.entity.Transaction;
import com.example.rewards.exception.TransactionNotFoundException;
import com.example.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RewardsServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    RewardsService rewardsService;

    @Test
    void testGetAllRewards() {
        Transaction t1 = new Transaction(1L, 1001L, 120.0, LocalDate.now());
        Transaction t2 = new Transaction(2L, 1001L, 75.0, LocalDate.now().minusMonths(1));
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<CustomerRewardsDTO> result = rewardsService.getAllRewards();
        assertEquals(115, result.get(0).getTotalPoints());
    }

    @Test
    void testGetAllRewards_NoTransactionsFound() {
        when(transactionRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(TransactionNotFoundException.class, () -> rewardsService.getAllRewards());
    }

    @Test
    void testGetRewardsByCustomerId() {
        Long customerId = 1001L;
        Transaction t1 = new Transaction(1L, customerId, 45.0, LocalDate.now());
        Transaction t2 = new Transaction(2L, customerId, 99.0, LocalDate.now());

        when(transactionRepository.findByCustomerId(customerId)).thenReturn(Arrays.asList(t1, t2));

        CustomerRewardsDTO result = rewardsService.getRewardsByCustomerId(customerId);
        assertEquals(49, result.getTotalPoints());
    }

    @Test
    void testGetRewardsByCustomerId_NoTransactionsFound() {
        Long customerId = 1001L;
        when(transactionRepository.findByCustomerId(customerId)).thenReturn(Collections.emptyList());
        assertThrows(TransactionNotFoundException.class, () -> rewardsService.getRewardsByCustomerId(customerId));
    }

    @Test
    void testCalculateRewardSummary() {
        Long customerId = 1001L;
        LocalDate now = LocalDate.now();

        Transaction oldTransaction = new Transaction(1L, customerId, 150.0, now.minusMonths(4));
        Transaction recentTransaction = new Transaction(2L, customerId, 110.0, now);

        List<Transaction> transactions = Arrays.asList(oldTransaction, recentTransaction);

        CustomerRewardsDTO rewards = rewardsService.calculateRewardSummary(customerId, transactions);

        assertEquals(70, rewards.getTotalPoints());
        assertEquals(1, rewards.getMonthlyRewards().size());
    }

    @Test
    void testCalculatePoints() {
        Long customerId = 1001L;
        LocalDate now = LocalDate.now();

        Transaction noPoints = new Transaction(1L, customerId, 40.0, now);
        Transaction lessThan50 = new Transaction(2L, customerId, 70.0, now);
        Transaction greaterThan100 = new Transaction(3L, customerId, 120.0, now);

        List<Transaction> transactions = Arrays.asList(noPoints, lessThan50, greaterThan100);
        CustomerRewardsDTO result = rewardsService.calculateRewardSummary(customerId, transactions);

        assertEquals(110, result.getTotalPoints());
    }
}



