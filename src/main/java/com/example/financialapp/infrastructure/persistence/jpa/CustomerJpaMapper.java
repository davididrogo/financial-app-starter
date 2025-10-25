package com.example.financialapp.infrastructure.persistence.jpa;

import com.example.financialapp.domain.model.Customer;

public final class CustomerJpaMapper {
    private CustomerJpaMapper(){}
    public static Customer toDomain(CustomerEntity e){
        return Customer.restore(e.getId(), e.getFirstName(), e.getLastName(), e.getEmail(),
                e.getCreatedAt());
    }
    public static void toEntity(Customer domain, CustomerEntity target){
        target.setId(domain.getId());
        target.setFirstName(domain.getFirstName());
        target.setLastName(domain.getLastName());
        target.setEmail(domain.getEmail());
        target.setCreatedAt(domain.getCreatedAt());
    }
}
