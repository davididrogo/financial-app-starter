package com.example.financialapp.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private final Instant createdAt;
    private Customer(UUID id, String firstName, String lastName, String email, Instant createdAt) {
        this.id = Objects.requireNonNull(id);
        rename(firstName, lastName);
        changeEmail(email);
        this.createdAt = createdAt == null ? Instant.now() : createdAt;
    }
    public static Customer register(String firstName, String lastName, String email) {
        return new Customer(UUID.randomUUID(), firstName, lastName, email, Instant.now());
    }
    public static Customer restore(UUID id, String firstName, String lastName, String email, Instant createdAt) {
        return new Customer(id, firstName, lastName, email, createdAt);
    }
    public void rename(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name required");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name required");
        }
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    public void changeEmail(String newEmail) {
        if (newEmail == null || !newEmail.contains("@")) throw new IllegalArgumentException("Invalid email");
        this.email = newEmail.trim().toLowerCase();
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public Instant getCreatedAt() { return createdAt; }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

}
