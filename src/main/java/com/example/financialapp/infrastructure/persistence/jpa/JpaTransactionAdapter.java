package com.example.financialapp.infrastructure.persistence.jpa;

import com.example.financialapp.application.port.out.AppendTransactionPort;
import com.example.financialapp.application.port.out.LoadAccountPort;
import com.example.financialapp.application.port.out.SaveAccountPort;
import com.example.financialapp.domain.model.Account;
import com.example.financialapp.domain.model.Transaction;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
@Primary
@Component("jpaTransactionAdapter")
public class JpaTransactionAdapter implements AppendTransactionPort {
    private final AccountAppendDelegate delegate;

    public JpaTransactionAdapter(AccountAppendDelegate delegate) {
        this.delegate = delegate;
    }
    @Override
    public void append(Transaction tx) {
        delegate.append(tx);
    }
}
