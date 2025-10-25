package com.example.financialapp.infrastructure.config;

import com.example.financialapp.application.events.PublishDomainEventPort;
import com.example.financialapp.application.port.in.AccountUseCase;
import com.example.financialapp.application.port.out.*;
import com.example.financialapp.application.service.AccountService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableScheduling
@ComponentScan(basePackages = "com.example.financialapp")
@EnableJpaRepositories(basePackages = "com.example.financialapp.infrastructure.persistence.jpa")
@EntityScan(basePackages = "com.example.financialapp.infrastructure.persistence.jpa")
public class AppConfig {
    @Bean
    public AccountUseCase accountUseCase(LoadAccountPort load,
                                         SaveAccountPort save,
                                         AppendTransactionPort append,
                                         PublishDomainEventPort publisher) {
        return new AccountService(load, save, append, publisher);
    }

}
