package com.example.financialapp.application.port.out;

import java.util.UUID;

public interface IdempotencyPort {
    boolean exist(String key, UUID accountId);
    void store(String key, UUID accountId);
}
