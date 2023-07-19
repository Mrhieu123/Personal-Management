package com.group6.moneymanagementbooking.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerController {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(HttpServletRequest req, AccessDeniedException ex) {
        log.error("AccessDeniedException: {}", ex.getMessage());
        return "errors/403";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SystemUnauthorizationException.class)
    public String handleUnauthorizationException(HttpServletRequest req, SystemUnauthorizationException ex) {
        log.error("UnauthorizationException: {}", ex.getMessage());
        return "errors/401";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SystemBadRequestException.class)
    public String handleBadRequestException(HttpServletRequest req, SystemBadRequestException ex) {
        log.error("BadRequestException: {}", ex.getMessage());
        return "errors/400";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException: {}", ex.getMessage());
        return "errors/400";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ SystemNotFoundException.class })
    public String handleNotFoundException(HttpServletRequest req, SystemNotFoundException ex) {
        log.error("NotFoundException: {}", ex.getMessage());
        return "errors/404";
    }

    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // @ExceptionHandler(Exception.class)
    // public String handleException(HttpServletRequest req, Exception ex) {
    // log.error("Exception: {}", ex.getMessage());
    // return "errors/500";
    // }

}