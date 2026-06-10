package com.example.EventManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(
        name = "booking_seats",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "show_seat_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingSeat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "booking_id",
            nullable = false
    )
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "show_seat_id",
            nullable = false
    )
    private ShowSeat showSeat;

    @Column(nullable = false)
    private BigDecimal price;
}
