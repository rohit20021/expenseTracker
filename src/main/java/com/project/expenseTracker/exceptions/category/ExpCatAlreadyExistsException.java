package com.project.expenseTracker.exceptions.category;

import lombok.Getter;

@Getter
public class ExpCatAlreadyExistsException extends RuntimeException {
    public String errorCode;

    public ExpCatAlreadyExistsException(String message,String errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
