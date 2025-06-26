package com.example.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardSummaryDTO {
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
