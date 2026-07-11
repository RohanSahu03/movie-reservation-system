package com.movie.theater_service.entity;

import com.movie.theater_service.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "seats",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"screen_id", "seat_number"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A1
     * A2
     * A3
     * B1
     * B2
     */
    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    /**
     * Row name
     * A
     * B
     * C
     */
    @Column(nullable = false, length = 5)
    private String seatRow;

    /**
     * Seat number in row
     * A5 -> 5
     */
    @Column(nullable = false)
    private Integer seatColumn;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatType seatType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

}