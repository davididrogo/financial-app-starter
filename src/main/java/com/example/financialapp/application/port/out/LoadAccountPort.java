package com.example.financialapp.application.port.out;

import com.example.financialapp.domain.model.Account;
import java.util.Optional;
import java.util.UUID;

public interface LoadAccountPort {
    Optional<Account> load(UUID id);
}
