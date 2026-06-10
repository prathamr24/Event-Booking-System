package com.example.EventManagementSystem.controller;

import com.example.EventManagementSystem.dto.request.MovieRequest;
import com.example.EventManagementSystem.dto.response.MovieResponse;
import com.example.EventManagementSystem.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public MovieResponse createMovie(
            @Valid
            @RequestBody
            MovieRequest request
    ) {

        return movieService.createMovie(
                request
        );
    }

    @GetMapping
    public List<MovieResponse> getAllMovies() {

        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public MovieResponse getMovieById(
            @PathVariable Long id
    ) {

        return movieService.getMovieById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMovie(
            @PathVariable Long id
    ) {

        movieService.deleteMovie(id);
    }
}
