package com.example.financialapp.application.port.out;

import java.util.UUID;

public interface DeleteCustomerPort {
    void deleteById(UUID id);
}
