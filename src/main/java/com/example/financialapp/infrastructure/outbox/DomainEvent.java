package com.example.financialapp.infrastructure.outbox;

public record DomainEvent(
        String type,
        String aggregateType,
        String aggregateId,
        Object data) {
}
