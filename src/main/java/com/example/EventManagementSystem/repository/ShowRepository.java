package com.example.EventManagementSystem.repository;

import com.example.EventManagementSystem.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ShowRepository
        extends JpaRepository<Show, Long> {

    @Query("""
            SELECT COUNT(s) > 0
            FROM Show s
            WHERE s.screen.id = :screenId
            AND s.status <> 'CANCELLED'
            AND (
                    :startTime < s.endTime
                    AND
                    :endTime > s.startTime
            )
            """)
    boolean existsConflictingShow(
            Long screenId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}