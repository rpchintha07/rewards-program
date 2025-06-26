CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    amount DOUBLE,
    date DATE
);
