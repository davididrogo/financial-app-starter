package com.example.financialapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FinancialAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinancialAppApplication.class, args);
    }
}
