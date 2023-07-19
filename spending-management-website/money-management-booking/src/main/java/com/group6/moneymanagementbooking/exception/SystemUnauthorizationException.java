package com.group6.moneymanagementbooking.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// import java.io.Serial;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SystemUnauthorizationException extends RuntimeException {

    // @Serial
    // private static final long serialVersionUID = 1372210541405289235L;

    public SystemUnauthorizationException(String message) {
        super(message);
    }
}
