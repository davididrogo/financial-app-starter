package com.example.financialapp.infrastructure.config;

import com.example.financialapp.application.events.PublishDomainEventPort;
import com.example.financialapp.application.port.in.AccountUseCase;
import com.example.financialapp.infrastructure.outbox.DomainEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Configuration
public class DecoratorConfig {
    @Bean
    @Primary
    public AccountUseCase accountUseCaseWithEvents(AccountUseCase core,
                                                   PublishDomainEventPort events){
        return (AccountUseCase) Proxy.newProxyInstance(
                AccountUseCase.class.getClassLoader(),
                new Class[]{AccountUseCase.class},
                (proxy, onlyMethodNeeded, args) -> {
                    //automatic light delegetation
                    Object delegateLikeResult = onlyMethodNeeded.invoke(core, args);
                    if(onlyMethodNeeded.getName().equals("deposit")){
                        UUID accountId = (UUID) args[0];
                        BigDecimal amount = (BigDecimal) args[1];
                        String txId = UUID.randomUUID().toString();

                        events.publish(new DomainEvent(
                                "FundsDeposited", "Account", accountId.toString(),
                                Map.of("txtId", txId, "amount", amount.toPlainString(),
                                        "ocurredAt", Instant.now().toString())));
                    }
                    return delegateLikeResult;
                });
    }
}
