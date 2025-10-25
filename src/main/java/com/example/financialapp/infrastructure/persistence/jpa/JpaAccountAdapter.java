package com.example.financialapp.infrastructure.persistence.jpa;

import com.example.financialapp.application.port.out.AppendTransactionPort;
import com.example.financialapp.application.port.out.LoadAccountPort;
import com.example.financialapp.application.port.out.SaveAccountPort;
import com.example.financialapp.domain.model.Account;
import com.example.financialapp.domain.model.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component("jpaAccountAdapterImpl")
@Transactional
public class JpaAccountAdapter implements LoadAccountPort, SaveAccountPort, AccountAppendDelegate {
    private final SpringDataAccountRepository accountRepo;
    private final SpringDataTransactionRepository txRepo;

    public JpaAccountAdapter(SpringDataAccountRepository accountRepo, SpringDataTransactionRepository txRepo) {
        this.accountRepo = accountRepo;
        this.txRepo = txRepo;
    }

    @Override
    public Optional<Account> loadAccount(UUID id) {
        return accountRepo.findById(id)
                .map(this::toDomain);
    }

    private Account toDomain(AccountEntity e){
        Account acc = new Account(e.getId(), e.getOwnerId(), e.getBalance());
        acc.setFrozen(e.isFrozen());
        return acc;
    }
    private AccountEntity toEntity(Account d) {
        return new AccountEntity(d.getId(), d.getOwnerUserId(), d.getBalance(), d.isFrozen());
    }
    @Override
    public List<Transaction> loadTransactions(UUID accountId) {
        return txRepo.findByAccountIdOrderByOccurredAtDesc(accountId)
                .stream()
                .map(e -> new Transaction(e.getId(), e.getAccountId(), e.getType(),
                        e.getAmount(), e.getOccurredAt()))
                .toList();
    }
    @Override
    public void save(Account account) {
        accountRepo.save(toEntity(account));
    }

    @Override
    public void append(Transaction tx) {
        txRepo.save(TransactionEntity.fromDomain(tx));
    }
}
