package com.example.EventManagementSystem.exception;

public class BookingAccessDeniedException
        extends RuntimeException {

    public BookingAccessDeniedException(
            String message
    ) {
        super(message);
    }
}