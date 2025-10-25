package com.example.financialapp.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
    public enum Type { DEPOSIT, WITHDRAWAL, TRANSFER }
    private final UUID id;
    private final UUID accountId;
    private final Type type;
    private final BigDecimal amount;
    private final Instant occurredAt;

    public Transaction(UUID id, UUID accountId, Type type, BigDecimal amount, Instant occurredAt) {
        this.id = Objects.requireNonNull(id);
        this.accountId = Objects.requireNonNull(accountId);
        this.type = Objects.requireNonNull(type);
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("amount must be > 0");
        this.amount = amount;
        this.occurredAt = occurredAt == null ? Instant.now() : occurredAt;
    }
    public static Transaction depositOf(UUID accountId, BigDecimal amount){
        return new Transaction(UUID.randomUUID(), accountId, Type.DEPOSIT, amount, Instant.now());
    }
    public static Transaction withdrawalOf(UUID accountId, BigDecimal amount){
        return new Transaction(UUID.randomUUID(), accountId, Type.WITHDRAWAL, amount,
                Instant.now());
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
