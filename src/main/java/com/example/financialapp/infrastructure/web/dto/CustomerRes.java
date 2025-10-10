package com.example.financialapp.infrastructure.web.dto;

import lombok.*;

public record CustomerRes (
     Long id,
     String firstName,
     String lastName,
     String email
){}
