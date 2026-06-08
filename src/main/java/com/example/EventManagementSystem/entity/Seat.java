package com.example.EventManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "seats",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "screen_id",
                                "seat_number"
                        }
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "screen_id",
            nullable = false
    )
    private Screen screen;

    @Column(nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private String rowLabel;

    @Column(nullable = false)
    private Integer seatIndex;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatType seatType;
}