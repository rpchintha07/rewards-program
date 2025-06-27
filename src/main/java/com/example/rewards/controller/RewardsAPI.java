package com.example.rewards.controller;

import com.example.rewards.dto.CustomerRewardsDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Validated
public interface RewardsAPI {

    ResponseEntity<List<CustomerRewardsDTO>> getRewardsForAllCustomers();

    ResponseEntity<CustomerRewardsDTO> getRewardsByCustomerId(@Valid @PathVariable(value = "customerId")
                                                              @NotNull(message = "customer id should not be null") Long customerId);
}
