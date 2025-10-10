package com.example.financialapp.infrastructure.web;

import com.example.financialapp.application.service.CustomerService;
import com.example.financialapp.infrastructure.web.dto.CreateCustomerReq;
import com.example.financialapp.infrastructure.web.dto.CustomerRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public ResponseEntity<CustomerRes> create(@RequestBody CreateCustomerReq req){
        var saved = service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
