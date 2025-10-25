package com.example.financialapp.application.port.out;

import com.example.financialapp.domain.model.Account;
import com.example.financialapp.domain.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadAccountPort {
    Optional<Account> loadAccount(UUID id);
    List<Transaction> loadTransactions(UUID accountId);
}
