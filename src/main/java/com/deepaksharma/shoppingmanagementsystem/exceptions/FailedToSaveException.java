package com.deepaksharma.shoppingmanagementsystem.exceptions;

public class FailedToSaveException extends RuntimeException {
    public FailedToSaveException(String message) {
        super(message);
    }
}
