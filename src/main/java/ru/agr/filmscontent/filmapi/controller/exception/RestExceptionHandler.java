package ru.agr.filmscontent.filmapi.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import ru.agr.filmscontent.filmapi.security.jwt.InvalidJwtAuthenticationException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {InvalidJwtAuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorMessage> invalidAuthentication(Exception e, ServletWebRequest request) {
        return getErrorMessageResponseEntity(e, request, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> notFoundEntity(EntityNotFoundException e, ServletWebRequest request) {
        return getErrorMessageResponseEntity(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorMessage> otherException(IllegalArgumentException e, ServletWebRequest request) {
        return getErrorMessageResponseEntity(e, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> otherException(Exception e, ServletWebRequest request) {
        return getErrorMessageResponseEntity(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorMessage> getErrorMessageResponseEntity(Exception e, ServletWebRequest request, HttpStatus httpStatus) {
        ErrorMessage errorMessage = getErrorMessage(e, request, httpStatus);
        return new ResponseEntity<>(errorMessage, httpStatus);
    }

    private ErrorMessage getErrorMessage(Exception e, ServletWebRequest request, HttpStatus httpStatus) {
        return ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .message(isNull(e.getMessage()) ? e.getClass().getName() : e.getMessage())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .path(request.getRequest().getRequestURI())
                .build();
    }

}
