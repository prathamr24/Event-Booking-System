package com.example.EventManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(
        name = "screens",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "theater_id",
                                "name"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Screen extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "theater_id",
            nullable = false
    )
    private Theater theater;

    @NotBlank(message = "Screen name is required")
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScreenType screenType;

    @Column(nullable = false)
    private Integer totalSeats;
}