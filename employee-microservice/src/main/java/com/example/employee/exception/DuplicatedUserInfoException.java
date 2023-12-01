package com.example.employee.exception;

public class DuplicatedUserInfoException extends RuntimeException {
    public DuplicatedUserInfoException(String message) {
        super(message);
    }

    public DuplicatedUserInfoException() {
        super();
    }
}
