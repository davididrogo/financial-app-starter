package com.example.financialapp.infrastructure.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public record CreateCustomerReq (
    String firstName,
    String lastName,
    String email
){}
