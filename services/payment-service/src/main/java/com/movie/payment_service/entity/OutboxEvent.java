package com.movie.payment_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Aggregate Type
     * Example: PAYMENT
     */
    @Column(nullable = false)
    private String aggregateType;

    /*
     * Aggregate Id
     * Example: Payment Id
     */
    @Column(nullable = false)
    private Long aggregateId;

    /*
     * Event Type
     * Example:
     * PaymentCompletedEvent
     * PaymentFailedEvent
     */
    @Column(nullable = false)
    private String eventType;

    /*
     * JSON payload
     */
    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String payload;

    /*
     * Has this event been published?
     */
    @Builder.Default
    private boolean published = false;

    /*
     * Published time
     */
    private LocalDateTime publishedAt;
}