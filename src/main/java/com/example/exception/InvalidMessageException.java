package com.example.exception;

/**
 * Custom exception for handling invalid message scenarios.
 * This exception extends the checked Exception class.
 */

public class InvalidMessageException extends Exception{

    //Constructor that takes a custom error message.

    public InvalidMessageException(String message) {
        super(message);  // Pass the message to the parent Exception constructor
    }
}
