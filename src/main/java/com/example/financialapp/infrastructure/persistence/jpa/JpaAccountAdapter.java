package com.example.financialapp.infrastructure.persistence.jpa;

import com.example.financialapp.application.port.out.AppendTransactionPort;
import com.example.financialapp.application.port.out.LoadAccountPort;
import com.example.financialapp.application.port.out.SaveAccountPort;
import com.example.financialapp.domain.model.Account;
import com.example.financialapp.domain.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class JpaAccountAdapter implements LoadAccountPort, SaveAccountPort, AppendTransactionPort {
    private final SpringDataAccountRepository accountRepo;
    private final SpringDataTransactionRepository txRepo;

    public JpaAccountAdapter(SpringDataAccountRepository accountRepo, SpringDataTransactionRepository txRepo) {
        this.accountRepo = accountRepo;
        this.txRepo = txRepo;
    }

    @Override
    public Optional<Account> load(UUID id) {
        return accountRepo.findById(id)
                .map(e -> new Account(e.getId(), e.getOwnerId(), e.getBalance()));
    }

    @Override
    public void save(Account account) {
        AccountEntity e = new AccountEntity();
        e.setId(account.getId());
        e.setOwnerId(account.getOwnerUserId());
        e.setBalance(account.getBalance());
        accountRepo.save(e);
    }

    @Override
    public void append(Transaction tx) {
        TransactionEntity e = new TransactionEntity();
        e.setId(tx.getId());
        e.setAccountId(tx.getAccountId());
        e.setType(tx.getType().name());
        e.setAmount(tx.getAmount());
        e.setOccurredAt(tx.getOccurredAt());
        txRepo.save(e);
    }
}
