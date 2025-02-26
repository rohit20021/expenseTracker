package com.project.expenseTracker.exceptions;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {
    private String errorCode;

    public DataNotFoundException(String msg, String errorCode) {
        super(msg);
        this.errorCode=errorCode;
    }
}
