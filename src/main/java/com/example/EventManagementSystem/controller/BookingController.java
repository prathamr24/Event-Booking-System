package com.example.EventManagementSystem.controller;

import com.example.EventManagementSystem.dto.request.BookingRequest;
import com.example.EventManagementSystem.dto.response.BookingDetailsResponse;
import com.example.EventManagementSystem.dto.response.BookingResponse;
import com.example.EventManagementSystem.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public BookingResponse createBooking(
            @Valid
            @RequestBody
            BookingRequest request
    ) {

        return bookingService.createBooking(
                request
        );
    }

    @GetMapping("/my-bookings")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<BookingResponse> getMyBookings() {

        return bookingService.getMyBookings();
    }


    @GetMapping("/{bookingId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public BookingDetailsResponse getBookingDetails(
            @PathVariable Long bookingId
    ) {

        return bookingService.getBookingDetails(
                bookingId
        );
    }
}
