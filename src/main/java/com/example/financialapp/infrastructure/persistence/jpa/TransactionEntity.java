package com.example.financialapp.infrastructure.persistence.jpa;

import com.example.financialapp.domain.model.Transaction;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "transactions")
public class TransactionEntity {
    @Id private UUID id;
    @Column(nullable = false) UUID accountId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false) private Transaction.Type type;
    @Column(nullable = false, precision = 19, scale = 2) private BigDecimal amount;
    @Column(nullable = false) private Instant occurredAt;
    protected TransactionEntity(){}
    public TransactionEntity(UUID id, UUID accountId, Transaction.Type type, BigDecimal amount,
                             Instant occurredAt){

    }
    public static TransactionEntity fromDomain(Transaction t){
        return new TransactionEntity(t.getId(), t.getAccountId(), t.getType(), t.getAmount(), t.getOccurredAt());
    }
    public UUID getId() { return id; }
    public UUID getAccountId() { return accountId; }
    public Transaction.Type getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public Instant getOccurredAt() { return occurredAt; }
}
