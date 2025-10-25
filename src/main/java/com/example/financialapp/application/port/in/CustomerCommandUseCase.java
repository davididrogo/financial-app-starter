package com.example.financialapp.application.port.in;

import java.util.UUID;

public interface CustomerCommandUseCase {
    UUID register(String firstname, String lastname, String email);
    void updateProfile(UUID id, String firstName, String lastName, String newEmail);
    void delete(UUID id);
}
