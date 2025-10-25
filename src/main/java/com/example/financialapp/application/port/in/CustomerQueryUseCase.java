package com.example.financialapp.application.port.in;

import com.example.financialapp.domain.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerQueryUseCase {
    Optional<Customer> getById(UUID id);
    Optional<Customer> getByEmail(String email);
}
