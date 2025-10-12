package com.example.financialapp.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class AccountEntity {
    @Id @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;
    @Column(nullable = false)
    private UUID ownerId;
    @Column(nullable = false)
    private BigDecimal balance;

}
