package com.example.financialapp.application.service;

import com.example.financialapp.domain.model.Customer;
import com.example.financialapp.infrastructure.persistence.jpa.CustomerRepository;
import com.example.financialapp.infrastructure.web.dto.CreateCustomerReq;
import com.example.financialapp.infrastructure.web.dto.CustomerRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repo;
    @Transactional
    public CustomerRes create(CreateCustomerReq req){
        var entity = new Customer();
        entity.setFirstName(req.firstName());
        entity.setLastName(req.lastName());
        entity.setEmail(req.email());
        entity = repo.save(entity);
        return new CustomerRes(entity.getId(),entity.getFirstName(),
                entity.getLastName(), entity.getEmail());
    }
}
