package com.example.EventManagementSystem.exception;

public class InvalidSeatConfigurationException
        extends RuntimeException {

    public InvalidSeatConfigurationException(
            String message
    ) {
        super(message);
    }
}