package com.example.financialapp.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {
    private final UUID id;
    private final UUID ownerUserId;
    private BigDecimal balance;
    private boolean frozen;

    public Account(UUID id, UUID ownerId, BigDecimal balance/*, boolean frozen*/) {
        this.id = id;
        this.ownerUserId = ownerId;
        this.balance = balance == null ? BigDecimal.ZERO : balance;
        //this.frozen = frozen;
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
    public static Account openNew(UUID ownerUserId, String type){
        if(ownerUserId == null) throw new IllegalArgumentException("ownerUserId is required");
        return new Account(UUID.randomUUID(), ownerUserId, BigDecimal.ZERO);
    }
    public void ensureNotFrozen(){
        if(frozen) throw new IllegalStateException("Account is frozen");
    }
    public void deposit(BigDecimal amount){
        ensureNotFrozen();
        if(amount == null || amount.signum() <= 0) throw new IllegalArgumentException("" +
                "amount must be > 0");
        balance = balance.add(amount);
    }
    public void withdraw(BigDecimal amount){
        ensureNotFrozen();
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("amount must be > 0");
        if (balance.compareTo(amount) < 0) throw new IllegalStateException("Insufficient funds");
        balance = balance.subtract(amount);
    }
    public boolean isFrozen() { return frozen; }
    public void setFrozen(boolean frozen) { this.frozen = frozen; }
}
