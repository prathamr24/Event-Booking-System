package com.example.EventManagementSystem.service;

import com.example.EventManagementSystem.dto.request.MovieRequest;
import com.example.EventManagementSystem.dto.response.MovieResponse;
import com.example.EventManagementSystem.entity.Movie;
import com.example.EventManagementSystem.exception.MovieAlreadyExistsException;
import com.example.EventManagementSystem.exception.ResourceNotFoundException;
import com.example.EventManagementSystem.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieResponse createMovie(
            MovieRequest request
    ) {

        if (
                movieRepository.existsByTitle(
                        request.getTitle()
                )
        ) {

            throw new MovieAlreadyExistsException(
                    "Movie with title '"
                            + request.getTitle()
                            + "' already exists"
            );
        }

        Movie movie = Movie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .genres(request.getGenres())
                .languages(request.getLanguages())
                .durationMinutes(
                        request.getDurationMinutes()
                )
                .releaseDate(
                        request.getReleaseDate()
                )
                .posterUrl(
                        request.getPosterUrl()
                )
                .trailerUrl(
                        request.getTrailerUrl()
                )
                .certificate(
                        request.getCertificate()
                )
                .build();

        Movie savedMovie =
                movieRepository.save(movie);

        return mapToResponse(savedMovie);
    }

    private MovieResponse mapToResponse(
            Movie movie
    ) {

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .genres(movie.getGenres())
                .languages(movie.getLanguages())
                .durationMinutes(
                        movie.getDurationMinutes()
                )
                .releaseDate(
                        movie.getReleaseDate()
                )
                .posterUrl(movie.getPosterUrl())
                .trailerUrl(movie.getTrailerUrl())
                .certificate(
                        movie.getCertificate()
                )
                .build();
    }


    public List<MovieResponse> getAllMovies() {

        return movieRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public MovieResponse getMovieById(
            Long movieId
    ) {

        Movie movie = movieRepository
                .findById(movieId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Movie not found with id: "
                                        + movieId
                        ));

        return mapToResponse(movie);
    }

    public void deleteMovie(
            Long movieId
    ) {

        Movie movie = movieRepository
                .findById(movieId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Movie not found with id: "
                                        + movieId
                        ));

        movieRepository.delete(movie);
    }
}
