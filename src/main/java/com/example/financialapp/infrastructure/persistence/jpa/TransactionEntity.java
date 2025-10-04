package com.example.financialapp.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "transactions")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class TransactionEntity {
    @Id private UUID id;
    @Column(nullable = false) UUID accountId;
    @Column(nullable = false) private String type;
    @Column(nullable = false) private BigDecimal amount;
    @Column(nullable = false) private Instant occurredAt;
}
