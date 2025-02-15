package com.example.exception;

/**
 * Custom exception for handling invalid account scenarios.
 * This exception extends RuntimeException, indicating that it is an unchecked exception.
 */

public class InvalidAccountException extends RuntimeException {
    public InvalidAccountException(String message) {
        super(message); // Pass the message to the parent RuntimeException constructor

    }
}
