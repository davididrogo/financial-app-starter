package com.example.financialapp.application.port.out;

import com.example.financialapp.domain.model.Account;

public interface SaveAccountPort {
    void save(Account account);
}
