CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT not null PRIMARY KEY,
    customer_id BIGINT not null,
    transaction_amount Double not null ,
    transaction_date DATE not null
    );
