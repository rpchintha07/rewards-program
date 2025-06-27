package com.example.rewards.controller;

import com.example.rewards.dto.CustomerRewardsDTO;
import com.example.rewards.service.RewardsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RewardsControllerTest {

    @Mock
    RewardsService rewardsService;

    @InjectMocks
    RewardsController rewardsController;

    @Test
    void testGetRewardsForAllCustomers() {
        CustomerRewardsDTO dto1 = new CustomerRewardsDTO(1L, 100, Collections.emptyList());
        CustomerRewardsDTO dto2 = new CustomerRewardsDTO(2L, 150, Collections.emptyList());

        List<CustomerRewardsDTO> rewardsList = Arrays.asList(dto1, dto2);
        when(rewardsService.getAllRewards()).thenReturn(rewardsList);

        ResponseEntity<List<CustomerRewardsDTO>> response = rewardsController.getRewardsForAllCustomers();

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(rewardsService, times(1)).getAllRewards();
    }

    @Test
    void testGetRewardsByCustomerId_Success() {
        Long customerId = 1001L;
        CustomerRewardsDTO dto = new CustomerRewardsDTO(customerId, 100, Collections.emptyList());

        when(rewardsService.getRewardsByCustomerId(customerId)).thenReturn(dto);

        ResponseEntity<CustomerRewardsDTO> response = rewardsController.getRewardsByCustomerId(customerId);

        assertNotNull(response.getBody());
        assertEquals(customerId, response.getBody().getCustomerId());
        verify(rewardsService, times(1)).getRewardsByCustomerId(customerId);
    }
}

