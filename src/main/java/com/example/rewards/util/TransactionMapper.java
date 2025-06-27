package com.example.rewards.util;

import com.example.rewards.dto.TransactionsDTO;
import com.example.rewards.entity.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    List<TransactionsDTO> toDTOList(List<Transaction> entities);
}

