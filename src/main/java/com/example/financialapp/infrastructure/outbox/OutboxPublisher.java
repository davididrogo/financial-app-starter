package com.example.financialapp.infrastructure.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OutboxPublisher {
    private final OutboxEventRepository repo;
    private final ObjectMapper mapper;
    public OutboxPublisher(OutboxEventRepository repo,ObjectMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }
    @Transactional
    public void publish(DomainEvent e){
        try{
            String payload = mapper.writeValueAsString(e);
            var evt = new OutboxEvent(UUID.randomUUID(),
                    e.aggregateType(), e.aggregateId(), e.type(), payload);
            repo.save(evt);
        }catch(JsonProcessingException ex){
            throw new RuntimeException("Failed to serialize event", ex);
        }
    }
}
