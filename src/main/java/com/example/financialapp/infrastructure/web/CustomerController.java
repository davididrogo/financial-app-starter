package com.example.financialapp.infrastructure.web;

import com.example.financialapp.application.port.in.CustomerCommandUseCase;
import com.example.financialapp.application.port.in.CustomerQueryUseCase;
import com.example.financialapp.domain.model.Customer;
import com.example.financialapp.infrastructure.web.dto.CreateCustomerReq;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerCommandUseCase command;
    private final CustomerQueryUseCase query;
    public CustomerController(CustomerCommandUseCase command,
                              CustomerQueryUseCase query) {
        this.command = command;
        this.query = query;
    }

    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @PostMapping
    public ResponseEntity<Map<String, UUID>> create(@RequestBody CreateCustomerReq req){
        var uuid = command.register(req.firstName(), req.lastName(), req.email());
        return ResponseEntity.ok(Map.of("id", uuid));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable("id") UUID id){
        Optional<Customer> found = query.getById(id);
        return found
                .map(c -> ResponseEntity.ok(CustomerResponse.from(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProfile(@PathVariable("id") UUID id,
                                              @RequestBody CreateCustomerReq req){
        command.updateProfile(id, req.firstName(), req.lastName(), req.email());
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        command.delete(id);
        return ResponseEntity.noContent().build();
    }
    public record CustomerResponse(
            UUID id,
            String firstName,
            String lastName,
            String email,
            String createdAt
    ){
        public static CustomerResponse from(Customer c){
            return new CustomerResponse(
                    c.getId(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getEmail(),
                    c.getCreatedAt().toString()
            );
        }
    }
}
