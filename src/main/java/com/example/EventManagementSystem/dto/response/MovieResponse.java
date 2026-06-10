package com.example.EventManagementSystem.dto.response;

import com.example.EventManagementSystem.entity.Certificate;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class MovieResponse {

    private Long id;

    private String title;

    private String description;

    private Set<String> genres;

    private Set<String> languages;

    private Integer durationMinutes;

    private LocalDate releaseDate;

    private String posterUrl;

    private String trailerUrl;

    private Certificate certificate;
}
