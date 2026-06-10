package com.example.EventManagementSystem.exception;

public class MovieAlreadyExistsException
        extends RuntimeException {

    public MovieAlreadyExistsException(
            String message
    ) {
        super(message);
    }
}