package com.example.financialapp.application.port.out;

import com.example.financialapp.domain.model.Transaction;

public interface AppendTransactionPort {
    void append(Transaction tx);
}
