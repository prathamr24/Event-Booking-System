package com.example.EventManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(
        name = "movies",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = "title"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Movie title is required")
    @Column(nullable = false, unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id")
    )
    @Column(name = "genre")
    private Set<String> genres;

    @ElementCollection
    @CollectionTable(
            name = "movie_languages",
            joinColumns = @JoinColumn(name = "movie_id")
    )
    @Column(name = "language")
    private Set<String> languages;

    @NotNull(message = "Duration is required")
    @Column(nullable = false)
    private Integer durationMinutes;

    @NotNull(message = "Release date is required")
    @Column(nullable = false)
    private LocalDate releaseDate;

    private String posterUrl;

    private String trailerUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Certificate certificate;
}