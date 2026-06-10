package com.example.EventManagementSystem.exception;

public class InvalidShowSeatException
        extends RuntimeException {

    public InvalidShowSeatException(
            String message
    ) {
        super(message);
    }
}