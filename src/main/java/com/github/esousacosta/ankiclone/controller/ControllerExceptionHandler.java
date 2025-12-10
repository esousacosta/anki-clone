package com.github.esousacosta.ankiclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse notFoundHandler(NoSuchElementException ex) {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> invalidArgumentHandler(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(ControllerExceptionHandler::toValidationError).collect(Collectors.toList());

        ValidationErrors validationErrors = new ValidationErrors(errors);
        ValidationErrorResponse response = new ValidationErrorResponse(
                "/problems/validation_error",
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "One or more validation errors occurred.",
                validationErrors
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentHandler(IllegalArgumentException ex) {
      ErrorResponse response = ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
      return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorResponse> securityExceptionHandler(SecurityException ex) {
        ErrorResponse response = ErrorResponse.create(ex, HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    private static ValidationError toValidationError(FieldError fieldError) {
        return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getCode());
    }


    private record ValidationError(String field, String message, String code) { }
    private record ValidationErrors(List<ValidationError> errors) {}
    private record ValidationErrorResponse(String type, int status, String title, String detail, ValidationErrors validationErrors) { }
}
