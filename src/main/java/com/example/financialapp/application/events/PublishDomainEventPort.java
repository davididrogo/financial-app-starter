package com.example.financialapp.application.events;

import com.example.financialapp.infrastructure.outbox.DomainEvent;

public interface PublishDomainEventPort {
    void publish(DomainEvent event);
}
