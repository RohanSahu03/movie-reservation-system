package com.movie.payment_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.payment_service.entity.OutboxEvent;
import com.movie.payment_service.repository.OutboxEventRepository;
import com.movie.payment_service.service.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxServiceImpl implements OutboxService {

    private final OutboxEventRepository outboxEventRepository;

    private final ObjectMapper objectMapper;

    @Override
    public void saveEvent(
            String aggregateType,
            Long aggregateId,
            String eventType,
            Object event
    ) {

        try {

            String payload =
                    objectMapper.writeValueAsString(event);

            OutboxEvent outboxEvent =
                    OutboxEvent.builder()
                            .aggregateType(aggregateType)
                            .aggregateId(aggregateId)
                            .eventType(eventType)
                            .payload(payload)
                            .published(false)
                            .build();

            outboxEventRepository.save(outboxEvent);

            log.info(
                    "Outbox event saved successfully : {}",
                    eventType
            );

        } catch (JsonProcessingException ex) {

            throw new RuntimeException(
                    "Failed to serialize event",
                    ex
            );
        }
    }
}