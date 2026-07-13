package com.movie.show_service.entity;

import com.movie.show_service.enums.ShowStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "shows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Show extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long movieId;

    @Column(nullable = false)
    private Long theaterId;

    @Column(nullable = false)
    private Long screenId;

    @Column(nullable = false)
    private LocalDate showDate;


    @Column(nullable = false)
    private LocalTime startTime;


    @Column(nullable = false)
    private LocalDateTime showStartTime;


    @Column(nullable = false)
    private LocalDateTime showEndTime;


    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal ticketPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ShowStatus status = ShowStatus.SCHEDULED;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;


}