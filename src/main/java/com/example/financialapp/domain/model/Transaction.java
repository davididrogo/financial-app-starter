package com.example.financialapp.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Transaction {
    public enum Type { DEPOSIT, WITHDRAWAL, TRANSFER }
    private final UUID id;
    private final UUID accountId;
    private final Type type;
    private final BigDecimal amount;
    private final Instant occurredAt;

    public Transaction(UUID id, UUID accountId, Type type, BigDecimal amount, Instant occurredAt) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.occurredAt = occurredAt;
    }
    public UUID getId() {
        return id;
    }
    public UUID getAccountId() {
        return accountId;
    }
    public Type getType() {
        return type;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public Instant getOccurredAt() {
        return occurredAt;
    }
}
