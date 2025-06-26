# Rewards Program Spring Boot API

A RESTful API for a retailer rewards program, calculating points for customers based on their purchase history.

---

## Reward Calculation Rules

- 2 points for every $1 spent **over $100** in each transaction  
- 1 point for every $1 spent **between $50 and $100** in each transaction  
- No points for $50 or less

**Example:**  
A $120 purchase earns 90 points:  
- 2 × $20 (over $100) = 40  
- 1 × $50 (from $50 to $100) = 50  
- **Total = 90 points**

---

## API Specification

### 1. Get All Rewards for All Customers

**GET** `/rewards`

- **Description:** Returns monthly and total rewards for every customer in the system.

**Response** (`200 OK`)
```json
[
  {
    "customerId": 1,
    "totalPoints": 370,
    "monthlyRewards": [
      { "month": "2024-03", "points": 250 },
      { "month": "2024-04", "points": 30 },
      { "month": "2024-05", "points": 90 }
    ]
  },
  {
    "customerId": 2,
    "totalPoints": 95,
    "monthlyRewards": [
      { "month": "2024-04", "points": 90 },
      { "month": "2024-05", "points": 5 }
    ]
  }
  // ...more customers
]
