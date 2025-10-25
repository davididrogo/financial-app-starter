package com.example.financialapp.application.port.out;

import java.util.Optional;
import java.util.UUID;

public interface LoadLastHashPort {
    Optional<String> lastHashOf(UUID accountId);
}
