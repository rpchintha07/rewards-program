package com.example.rewards.service;

import com.example.rewards.dto.TransactionsDTO;
import com.example.rewards.entity.Transaction;
import com.example.rewards.exception.TransactionNotFoundException;
import com.example.rewards.repository.TransactionRepository;
import com.example.rewards.util.TransactionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionsServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    TransactionMapper transactionMapper;

    @InjectMocks
    TransactionsService transactionsService;

    @Test
    void testGetTransactionsByCustomerId() {
        Long customerId = 1001L;

        Transaction entity1 = new Transaction(1L, customerId, 100.0, LocalDate.now());
        Transaction entity2 = new Transaction(1L, customerId, 100.0, LocalDate.now());
        List<Transaction> entityList = Arrays.asList(entity1, entity2);

        TransactionsDTO dto1 = new TransactionsDTO(1L, customerId, 100.0, LocalDate.now());
        TransactionsDTO dto2 = new TransactionsDTO(1L, customerId, 100.0, LocalDate.now());

        List<TransactionsDTO> dtoList = Arrays.asList(dto1, dto2);

        when(transactionRepository.findByCustomerId(customerId)).thenReturn(entityList);
        when(transactionMapper.toDTOList(entityList)).thenReturn(dtoList);

        List<TransactionsDTO> result = transactionsService.getTransactionsByCustomerId(customerId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetTransactionsByCustomerId_EmptyList() {
        Long customerId = 1001L;
        when(transactionRepository.findByCustomerId(customerId)).thenReturn(Collections.emptyList());

        TransactionNotFoundException exception = assertThrows(TransactionNotFoundException.class,
                () -> transactionsService.getTransactionsByCustomerId(customerId));

        assertEquals("Transactions not found for the given customer id: " + customerId, exception.getMessage());
    }

    @Test
    void testGetTransactionsByCustomerId_Null() {
        Long customerId = 1001L;
        when(transactionRepository.findByCustomerId(customerId)).thenReturn(null);

        TransactionNotFoundException exception = assertThrows(TransactionNotFoundException.class,
                () -> transactionsService.getTransactionsByCustomerId(customerId));

        assertEquals("Transactions not found for the given customer id: " + customerId, exception.getMessage());
    }
}
