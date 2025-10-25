package com.example.financialapp.application.port.out;

import com.example.financialapp.domain.model.Customer;

public interface SaveCustomerPort {
    void save(Customer customer);
}
