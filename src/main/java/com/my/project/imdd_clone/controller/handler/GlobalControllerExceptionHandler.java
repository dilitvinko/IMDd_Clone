package com.my.project.imdd_clone.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage accessDeniedException(Exception ex) {
        return new ErrorMessage(HttpStatus.FORBIDDEN.value(), ex.getMessage(), "excephandler");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage authenticationException(Exception ex) {
        return new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), "excephandler");
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage resourceNotFoundException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Arrays.toString(ex.getStackTrace()));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage constraintViolationException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), Arrays.toString(ex.getStackTrace()));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage ValidationException(MethodArgumentNotValidException ex) {
        log.debug(ex.getMessage(), ex);
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), errors.toString(), Arrays.toString(ex.getStackTrace()));
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage NoSuchElementException(NoSuchElementException ex) {
        log.debug(ex.getMessage(), ex);
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Arrays.toString(ex.getStackTrace()));
    }
}
