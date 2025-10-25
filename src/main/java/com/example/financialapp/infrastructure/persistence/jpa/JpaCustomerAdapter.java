package com.example.financialapp.infrastructure.persistence.jpa;

import com.example.financialapp.application.port.out.DeleteCustomerPort;
import com.example.financialapp.application.port.out.LoadCustomerPort;
import com.example.financialapp.application.port.out.SaveCustomerPort;
import com.example.financialapp.domain.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class JpaCustomerAdapter implements LoadCustomerPort, SaveCustomerPort, DeleteCustomerPort {
    private final CustomerRepository repo;
    public JpaCustomerAdapter(CustomerRepository repo){
        this.repo = repo;
    }

    @Override
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<Customer> loadById(UUID id) {
        return repo.findById(id)
                .map(CustomerJpaMapper::toDomain);
    }

    @Override
    public Optional<Customer> loadByEmail(String email) {
        return repo.findByEmailIgnoreCase(email)
                .map(CustomerJpaMapper::toDomain);
    }

    @Override
    public void save(Customer c) {
        CustomerEntity e = repo.findById(c.getId()).orElseGet(CustomerEntity::new);
        CustomerJpaMapper.toEntity(c, e);
        repo.save(e);
    }
}
