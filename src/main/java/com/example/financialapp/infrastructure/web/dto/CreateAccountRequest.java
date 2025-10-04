package com.example.financialapp.infrastructure.web.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @DecimalMin("0.00") BigDecimal initialAmount
        ) {}
