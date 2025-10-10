package com.example.financialapp.infrastructure.persistence.jpa;

import com.example.financialapp.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
