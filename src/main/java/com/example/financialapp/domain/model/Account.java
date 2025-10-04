package com.example.financialapp.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {
    private final UUID id;
    private final UUID ownerUserId;
    private BigDecimal balance;

    public Account(UUID id, UUID ownerId, BigDecimal balance) {
        this.id = id;
        this.ownerUserId = ownerId;
        this.balance = balance;
    }
    public UUID getId() {
        return id;
    }
    public UUID getOwnerUserId() {
        return ownerUserId;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }
    public void debit(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient funds");
        this.balance = this.balance.subtract(amount);
    }
}
