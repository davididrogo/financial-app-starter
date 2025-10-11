package com.example.financialapp.infrastructure.web.dto;

import lombok.*;

import java.util.UUID;

public record CustomerRes (
     UUID id,
     String firstName,
     String lastName,
     String email
){}
