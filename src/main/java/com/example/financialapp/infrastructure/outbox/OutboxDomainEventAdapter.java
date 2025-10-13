package com.example.financialapp.infrastructure.outbox;

import com.example.financialapp.application.events.PublishDomainEventPort;
import org.springframework.stereotype.Component;

@Component
public class OutboxDomainEventAdapter implements PublishDomainEventPort {
    private final OutboxPublisher outbox;
    public OutboxDomainEventAdapter(OutboxPublisher outbox){
        this.outbox = outbox;
    }
    @Override
    public void publish(DomainEvent event) {
        outbox.publish(new DomainEvent(event.type(),
                event.aggregateType(),
                event.aggregateId(),
                event.data()));
    }
}
