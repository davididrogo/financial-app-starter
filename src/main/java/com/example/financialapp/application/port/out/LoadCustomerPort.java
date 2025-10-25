package com.example.financialapp.application.port.out;

import com.example.financialapp.domain.model.Customer;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface LoadCustomerPort {
    Optional<Customer> loadById(UUID id);
    Optional<Customer> loadByEmail(String email);
}
