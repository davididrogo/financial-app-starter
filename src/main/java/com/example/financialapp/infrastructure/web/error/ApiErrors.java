package com.example.financialapp.infrastructure.web.error;


import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiErrors {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<Map<String, String>> badJson(HttpMessageNotReadableException ex){
        return ResponseEntity.badRequest().body(
                Map.of("error", "Invalid request",
                        "detail", ex.getMostSpecificCause().getMessage())
        );
    }
}
