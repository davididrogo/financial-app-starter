package com.example.financialapp.application.service;

import com.example.financialapp.application.port.in.CustomerCommandUseCase;
import com.example.financialapp.application.port.in.CustomerQueryUseCase;
import com.example.financialapp.application.port.out.DeleteCustomerPort;
import com.example.financialapp.application.port.out.LoadCustomerPort;
import com.example.financialapp.application.port.out.SaveCustomerPort;
import com.example.financialapp.domain.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class CustomerService implements CustomerCommandUseCase, CustomerQueryUseCase {
    private final LoadCustomerPort load;
    private final SaveCustomerPort save;
    private final DeleteCustomerPort delete;
    public CustomerService(LoadCustomerPort load, SaveCustomerPort save, DeleteCustomerPort delete) {
        this.load = load; this.save = save; this.delete = delete;
    }
    @Override
    public UUID register(String firstname, String lastname, String email) {
        Customer c = Customer.register(firstname, lastname, email);
        save.save(c);
        return c.getId();
    }

    @Override
    public void updateProfile(UUID id, String newFirstName, String newLastName, String newEmail) {
        Customer c = load.loadById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        if (newEmail != null) c.changeEmail(newEmail);
        if (newFirstName != null && newLastName != null) c.changeEmail(newEmail);
        save.save(c);
    }

    @Override
    public void delete(UUID id) {
        delete.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Customer> getById(UUID id) {
        return load.loadById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Customer> getByEmail(String email) {
        return Optional.empty();
    }
}
