package com.project.expenseTracker.exceptions;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {
    public String errorCode;
    public InvalidTokenException(String message,String errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
}
