package com.example.EventManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String genre;

    private String language;

    private Integer durationMinutes;

    private LocalDate releaseDate;

    private String posterUrl;

    private String trailerUrl;

    @Enumerated(EnumType.STRING)
    private Certificate certificate;
}