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
        name = "show_seats",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "show_id",
                                "seat_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSeat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "show_id",
            nullable = false
    )
    private Show show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "seat_id",
            nullable = false
    )
    private Seat seat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShowSeatStatus status;

    @Column(nullable = false)
    private BigDecimal price;
}