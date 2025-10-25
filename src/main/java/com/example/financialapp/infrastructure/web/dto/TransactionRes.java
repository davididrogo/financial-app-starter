package com.example.financialapp.infrastructure.web.dto;

import com.example.financialapp.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionRes(
        UUID id,
        UUID account,
        String type,
        BigDecimal amount,
        Instant at
) {
    public static TransactionRes from(Transaction t){
        return new TransactionRes(t.getId(), t.getAccountId(), t.getType().name(),
                t.getAmount(), t.getOccurredAt());
    }
}
