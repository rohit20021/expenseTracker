package com.project.expenseTracker.handler;

import com.project.expenseTracker.dto.ErrorResponseDto;
import com.project.expenseTracker.exceptions.InvalidCredentialsException;
import com.project.expenseTracker.exceptions.InvalidTokenException;
import com.project.expenseTracker.exceptions.UserAlreadyExistsException;
import com.project.expenseTracker.exceptions.UserNotFoundException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    ResponseEntity<ErrorResponseDto> handleUserAlreadyExists(UserAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponseDto.builder()
                                .errorCode(e.getErrorCode())
                                .errorMessage(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponseDto.builder()
                                .errorCode(e.getErrorCode())
                                .errorMessage(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    ResponseEntity<ErrorResponseDto> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponseDto.builder()
                                .errorCode(e.getErrorCode())
                                .errorMessage(e.getMessage())
                                .build()
                );
    }


    @ExceptionHandler(InvalidTokenException.class)
    ResponseEntity<ErrorResponseDto> handleInvalidTokenException(InvalidTokenException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponseDto.builder()
                                .errorCode(e.getErrorCode())
                                .errorMessage(e.getMessage())
                                .build()
                );
    }
}
