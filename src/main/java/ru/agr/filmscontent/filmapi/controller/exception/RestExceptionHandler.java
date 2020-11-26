package ru.agr.filmscontent.filmapi.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.agr.filmscontent.filmapi.security.jwt.InvalidJwtAuthenticationException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {InvalidJwtAuthenticationException.class})
    public ResponseEntity<ErrorMessage> invalidJwtAuthentication(InvalidJwtAuthenticationException e,
                                                      WebRequest request) {
        HttpStatus httpStatus =  HttpStatus.UNAUTHORIZED;

        ErrorMessage errorMessage = ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .path(request.getContextPath())
                .build();

        return new ResponseEntity<>(errorMessage, httpStatus);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> invalidJwtAuthentication(EntityNotFoundException e,
                                                                 WebRequest request) {
        HttpStatus httpStatus =  HttpStatus.BAD_REQUEST;

        ErrorMessage errorMessage = ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .path(request.getContextPath())
                .build();

        return new ResponseEntity<>(errorMessage, httpStatus);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> invalidJwtAuthentication(Exception e,
                                                                 WebRequest request) {
        HttpStatus httpStatus =  HttpStatus.BAD_REQUEST;

        ErrorMessage errorMessage = ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .path(request.getContextPath())
                .build();

        return new ResponseEntity<>(errorMessage, httpStatus);
    }

}
