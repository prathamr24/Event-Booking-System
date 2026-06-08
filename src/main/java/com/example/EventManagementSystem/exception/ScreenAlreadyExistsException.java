package com.example.EventManagementSystem.exception;

public class ScreenAlreadyExistsException
        extends RuntimeException {

    public ScreenAlreadyExistsException(
            String message
    ) {
        super(message);
    }
}
