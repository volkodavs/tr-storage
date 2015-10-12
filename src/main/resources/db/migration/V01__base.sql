CREATE TABLE transaction_storage (
    id BIGINT PRIMARY KEY,
    amount    DECIMAL(10,2),
    type      VARCHAR(100),
    parentid  BIGINT REFERENCES transaction_storage,
    mpath     VARCHAR(4000),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version   INTEGER NOT NULL DEFAULT 0
);

CREATE INDEX I1_TRANSACTION_STORAGE ON transaction_storage (type);

