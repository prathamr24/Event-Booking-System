package com.example.EventManagementSystem.repository;

import com.example.EventManagementSystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdOrderByBookingTimeDesc(
            Long userId
    );
}