package com.example.financialapp.application.service;

import com.example.financialapp.application.events.PublishDomainEventPort;
import com.example.financialapp.application.port.in.AccountUseCase;
import com.example.financialapp.application.port.out.*;
import com.example.financialapp.domain.model.Account;
import com.example.financialapp.domain.model.Transaction;
import com.example.financialapp.infrastructure.outbox.DomainEvent;
import com.example.financialapp.infrastructure.web.dto.TransactionRes;
import org.apache.kafka.common.security.oauthbearer.internals.secured.ValidateException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountService implements AccountUseCase {
    private final LoadAccountPort loadAccountPort;
    private final SaveAccountPort saveAccountPort;
    private final AppendTransactionPort appendTransactionPort;
    private final PublishDomainEventPort events;

    public AccountService(LoadAccountPort load, SaveAccountPort save, AppendTransactionPort tx,
                          PublishDomainEventPort events) {
        this.loadAccountPort = load;
        this.saveAccountPort = save;
        this.appendTransactionPort = tx;
        this.events = events;
    }

    @Override
    public UUID open(UUID ownerId, String type) {
        if (ownerId == null) throw new ValidateException("ownerId is required");
        Account acc = Account.openNew(ownerId, type);
        saveAccountPort.save(acc);
        events.publish(new DomainEvent(
                "AccountOpened",
                "Account",
                acc.getId().toString(),
                acc
        ));
        return acc.getId();
    }
    @Transactional
    @Override
    public void deposit(UUID accountId, BigDecimal amount) {
        requirePositive(amount);
        Account acc = require(loadAccountPort.loadAccount(accountId));
        acc.deposit(amount);
        saveAccountPort.save(acc);

        Transaction tx = Transaction.depositOf(accountId, amount);
        appendTransactionPort.append(tx);
        events.publish(new DomainEvent(
                "MoneyDeposited",              // type
                "Account",                      // aggregateType
                accountId.toString(),           // aggregateId
                tx                               // data
        ));
    }

    @Override
    public void withdraw(UUID accountId, BigDecimal amount) {
        requirePositive(amount);
        Account acc = require(loadAccountPort.loadAccount(accountId));
        acc.withdraw(amount);
        saveAccountPort.save(acc);

        Transaction tx = Transaction.withdrawalOf(accountId, amount);
        appendTransactionPort.append(tx);
        events.publish(new DomainEvent(
                "MoneyWithdrawn",
                "Account",
                accountId.toString(),
                tx));
    }

    @Override
    public List<TransactionRes> historyOf(UUID accountId) {
        return loadAccountPort.loadTransactions(accountId).stream()
                .map(TransactionRes::from)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal balanceOf(UUID id) {
        return require(loadAccountPort.loadAccount(id)).getBalance();
    }

    private static <T> T require(Optional<T> t) {
        return t.orElseThrow(() -> new IllegalArgumentException("Not found"));
    }

    private static void requirePositive(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0)
            throw new ValidateException("Amount must be > 0");
    }
}