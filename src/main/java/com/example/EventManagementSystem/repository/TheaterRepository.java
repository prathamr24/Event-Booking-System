package com.example.EventManagementSystem.repository;

import com.example.EventManagementSystem.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository
        extends JpaRepository<Theater, Long> {
}