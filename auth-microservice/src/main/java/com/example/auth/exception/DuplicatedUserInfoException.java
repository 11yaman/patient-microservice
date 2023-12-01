package com.example.auth.exception;

public class DuplicatedUserInfoException extends RuntimeException {
    public DuplicatedUserInfoException(String message) {
        super(message);
    }

    public DuplicatedUserInfoException() {
        super();
    }
}
