package com.movie.payment_service.service;

public interface OutboxService {

    void saveEvent(
            String aggregateType,
            Long aggregateId,
            String eventType,
            Object event
    );

}