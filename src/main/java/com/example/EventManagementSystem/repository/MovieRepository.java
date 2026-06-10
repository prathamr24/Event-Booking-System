package com.example.EventManagementSystem.repository;

import com.example.EventManagementSystem.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository
        extends JpaRepository<Movie, Long> {

    boolean existsByTitle(String title);
}