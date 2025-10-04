package com.example.financialapp.infrastructure.config;

import com.example.financialapp.application.port.in.AccountUseCase;
import com.example.financialapp.application.port.out.AppendTransactionPort;
import com.example.financialapp.application.port.out.LoadAccountPort;
import com.example.financialapp.application.port.out.SaveAccountPort;
import com.example.financialapp.application.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public AccountUseCase accountUseCase(LoadAccountPort load, SaveAccountPort save, AppendTransactionPort append) {
        return new AccountService(load, save, append);
    }
}
