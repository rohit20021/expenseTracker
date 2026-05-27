package com.project.expenseTracker.exceptions;

import lombok.Getter;

@Getter
public class InvalidCredentialsException extends RuntimeException{
    private String errorCode;

    public InvalidCredentialsException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
