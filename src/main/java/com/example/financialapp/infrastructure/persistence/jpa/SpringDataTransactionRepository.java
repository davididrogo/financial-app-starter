package com.example.financialapp.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataTransactionRepository extends JpaRepository<TransactionEntity, UUID> {}
