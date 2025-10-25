package com.example.financialapp.infrastructure.persistence.jpa;

import com.example.financialapp.domain.model.Transaction;

public interface AccountAppendDelegate {
    void append(Transaction tx);
}
