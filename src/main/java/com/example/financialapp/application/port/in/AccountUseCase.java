package com.example.financialapp.application.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountUseCase {
    UUID createAccount(UUID ownerId);
    void deposit(UUID accountId, BigDecimal amount);
    void withdraw(UUID accountId, BigDecimal amount);
}








