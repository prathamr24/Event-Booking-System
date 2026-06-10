package com.example.EventManagementSystem.dto.request;

import com.example.EventManagementSystem.entity.Certificate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class MovieRequest {

    @NotBlank(message = "Movie title is required")
    private String title;

    private String description;

    @NotEmpty(message = "At least one genre is required")
    private Set<String> genres;

    @NotEmpty(message = "At least one language is required")
    private Set<String> languages;

    @NotNull(message = "Duration is required")
    private Integer durationMinutes;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;

    private String posterUrl;

    private String trailerUrl;

    @NotNull(message = "Certificate is required")
    private Certificate certificate;
}
