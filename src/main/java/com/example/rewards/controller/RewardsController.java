package com.example.rewards.controller;

import com.example.rewards.dto.CustomerRewardsDTO;
import com.example.rewards.service.RewardsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RewardsController implements RewardsAPI {

    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @Override
    @GetMapping("/rewards")
    public ResponseEntity<List<CustomerRewardsDTO>> getRewardsForAllCustomers() {
        List<CustomerRewardsDTO> rewards = rewardsService.getAllRewards();
        return ResponseEntity.status(HttpStatus.OK).body(rewards);
    }

    @Override
    @GetMapping("/rewards/{customerId}")
    public ResponseEntity<CustomerRewardsDTO> getRewardsByCustomerId(@Valid @PathVariable(value = "customerId")
                                                                     @NotNull(message = "customer id should not be null") Long customerId) {
        CustomerRewardsDTO rewards = rewardsService.getRewardsByCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(rewards);
    }
}
