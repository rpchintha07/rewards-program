package com.example.rewards.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRewardsDTO {
    private Long customerId;
    private int totalPoints;
    private List<MonthlyReward> monthlyRewards;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyReward {
        private String month;
        private int points;
    }
}
