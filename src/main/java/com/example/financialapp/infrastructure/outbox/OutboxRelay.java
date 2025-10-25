package com.example.financialapp.infrastructure.outbox;

import com.example.financialapp.infrastructure.persistence.jpa.OutboxEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OutboxRelay {
    private static final Logger log = LoggerFactory.getLogger(OutboxRelay.class);
    private final OutboxEventRepository repo;
    private final KafkaTemplate<String, String> kafka;

    public OutboxRelay(OutboxEventRepository repo, KafkaTemplate<String, String> kafka){
        this.repo = repo;
        this.kafka = kafka;
    }
    @Scheduled(fixedDelay = 1000, initialDelay = 2000)
    @Transactional
    public void flush(){
        repo.findTop100ByPublishedFalseOrderByOccurredAtAsc().forEach(evt -> {
            kafka.send("account.events", evt.getAggregateId(), evt.getPayload());
            evt.setPublished(true);
            log.info("Outbox published {} {}", evt.getType(), evt.getId());
        });
    }

}
