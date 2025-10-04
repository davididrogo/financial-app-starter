package com.example.financialapp.infrastructure.web;

import com.example.financialapp.application.port.in.AccountUseCase;
import com.example.financialapp.infrastructure.web.dto.CreateAccountRequest;
import com.example.financialapp.infrastructure.web.dto.MoneyRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Accounts")
public class AccountController {
    private final AccountUseCase useCase;
    public AccountController(AccountUseCase useCase) { this.useCase = useCase; }

    @PostMapping
    public ResponseEntity<Void> create(@Validated @RequestBody CreateAccountRequest req,
                                       Authentication auth) {
        UUID userId = UUID.fromString(auth.getName());
        UUID accountId = useCase.createAccount(userId);

        if(req.initialAmount() != null
        && req.initialAmount().compareTo(BigDecimal.ZERO) > 0){
            useCase.deposit(accountId, req.initialAmount());
        }
        return ResponseEntity.created(URI.create("/api/v1/accounts/" + accountId)).build();
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable UUID id, @Validated @RequestBody MoneyRequest body) {
        useCase.deposit(id, body.amount());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable UUID id, @Validated @RequestBody MoneyRequest body) {
        useCase.withdraw(id, body.amount());
        return ResponseEntity.noContent().build();
    }
}
