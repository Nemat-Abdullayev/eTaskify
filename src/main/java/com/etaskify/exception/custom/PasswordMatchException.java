package com.etaskify.exception.custom;

public class PasswordMatchException extends RuntimeException {
    public PasswordMatchException() {
    }

    public PasswordMatchException(String message) {
        super(message);
    }
}
