package com.movie.payment_service.scheduler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.common.event.PaymentCompletedEvent;
import com.movie.common.event.PaymentFailedEvent;
import com.movie.payment_service.entity.OutboxEvent;
import com.movie.payment_service.producer.PaymentEventProducer;
import com.movie.payment_service.repository.OutboxEventRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxScheduler {


    private final OutboxEventRepository outboxRepository;

    private final PaymentEventProducer producer;

    private final ObjectMapper objectMapper;



    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishOutboxEvents(){


        List<OutboxEvent> events =
                outboxRepository
                        .findByPublishedFalseOrderByCreatedAtAsc();


        if(events.isEmpty()){
            return;
        }


        log.info(
                "Found {} unpublished events",
                events.size()
        );


        for(OutboxEvent event : events){


            try {


                switch(event.getEventType()){


                    case "PaymentCompletedEvent" -> {


                        PaymentCompletedEvent paymentEvent =
                                objectMapper.readValue(
                                        event.getPayload(),
                                        PaymentCompletedEvent.class
                                );


                        producer.sendPaymentCompletedEvent(
                                paymentEvent
                        ).get();

                    }


                    case "PaymentFailedEvent" -> {


                        PaymentFailedEvent paymentEvent =
                                objectMapper.readValue(
                                        event.getPayload(),
                                        PaymentFailedEvent.class
                                );


                        producer.sendPaymentFailedEvent(
                                paymentEvent
                        ).get();

                    }


                    default -> {

                        log.warn(
                                "Unknown event type {}",
                                event.getEventType()
                        );

                        continue;
                    }

                }



                event.setPublished(true);

                event.setPublishedAt(
                        LocalDateTime.now()
                );


                outboxRepository.save(event);



                log.info(
                        "Outbox event marked published id={}",
                        event.getId()
                );



            } catch(Exception ex){


                log.error(
                        "Failed publishing outbox event id={}",
                        event.getId(),
                        ex
                );

            }

        }

    }

}