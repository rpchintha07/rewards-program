package com.example.rewards.controller;

import com.example.rewards.dto.TransactionsDTO;
import com.example.rewards.service.TransactionsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionsControllerTest {
    @Mock
    TransactionsService transactionsService;

    @InjectMocks
    TransactionsController transactionsController;

    @Test
    void testGetTransactionsByCustomerId() {
        Long customerId = 1001L;

        TransactionsDTO dto1 = new TransactionsDTO(1L, customerId, 50.0, LocalDate.now());

        TransactionsDTO dto2 = new TransactionsDTO(2L, customerId, 75.0, LocalDate.now().minusDays(1));

        List<TransactionsDTO> mockList = Arrays.asList(dto1, dto2);

        when(transactionsService.getTransactionsByCustomerId(customerId)).thenReturn(mockList);

        ResponseEntity<List<TransactionsDTO>> response = transactionsController.getTransactionsByCustomerId(customerId);

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(dto1.getId(), response.getBody().get(0).getId());
        assertEquals(dto2.getId(), response.getBody().get(1).getId());

        verify(transactionsService, times(1)).getTransactionsByCustomerId(customerId);
    }
}

