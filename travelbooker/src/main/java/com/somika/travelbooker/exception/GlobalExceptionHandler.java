package com.somika.travelbooker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorDetails handleAllExceptions(Exception ex, WebRequest request) {
        System.out.println(ex.getClass().getName());
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ErrorDetails handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorDetails handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(errorMessage)
                .details(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorDetails handleAccountNotFoundException(AccountNotFoundException ex, WebRequest request) {
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ErrorDetails handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(AccountIsAlreadyActivatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorDetails handleAccountIsAlreadyActivatedException(AccountIsAlreadyActivatedException ex, WebRequest request) {
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(PasswordResetTokenNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorDetails handlePasswordResetTokenNotFoundException(PasswordResetTokenNotFoundException ex, WebRequest request) {
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(PasswordResetTokenExpiredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorDetails handlePasswordResetTokenExpiredException(PasswordResetTokenExpiredException ex, WebRequest request) {
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(SearchCriteriaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorDetails handleSearchCriteriaException(SearchCriteriaException ex, WebRequest request) {
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(DestinationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorDetails handleDestinationNotFoundException(DestinationNotFoundException ex, WebRequest request) {
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
    }
}
