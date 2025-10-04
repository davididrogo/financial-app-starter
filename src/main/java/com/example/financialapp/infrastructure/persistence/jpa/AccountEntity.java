package com.example.financialapp.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class AccountEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private UUID ownerId;
    @Column(nullable = false)
    private BigDecimal balance;

}
