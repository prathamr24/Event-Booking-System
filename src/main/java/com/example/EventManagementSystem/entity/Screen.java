package com.example.EventManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "screens")
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
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ScreenType screenType;

    private Integer totalSeats;
}