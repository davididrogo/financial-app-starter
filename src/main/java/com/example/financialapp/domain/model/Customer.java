package com.example.financialapp.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter @Setter
public class Customer {
    @Id @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
}
