package com.example.rewards.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.rewards.entity.Transaction;
import com.example.rewards.repository.TransactionRepository;
import com.example.rewards.service.RewardService;

@WebMvcTest(RewardController.class)
public class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    public void testGetAllTransactions() throws Exception {
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(
                new Transaction(1L, 1L, 120, LocalDate.now().minusMonths(1)),
                new Transaction(2L, 1L, 80, LocalDate.now().minusMonths(2))
        ));
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(120));
    }

    @Test
    public void testGetAllRewards() throws Exception {
        mockMvc.perform(get("/rewards"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRewardForCustomer() throws Exception {
        mockMvc.perform(get("/rewards/1"))
                .andExpect(status().isOk());
    }
}
