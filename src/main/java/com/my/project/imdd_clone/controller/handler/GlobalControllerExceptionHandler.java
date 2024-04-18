package com.my.project.imdd_clone.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage accessDeniedException(Exception ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.FORBIDDEN.value(), ex.getMessage(), "excephandler");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage authenticationException(Exception ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), "excephandler");
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage resourceNotFoundException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Arrays.toString(ex.getStackTrace()));
    }
}
