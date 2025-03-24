package com.store.inventory_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED) // Returns 401 status code
public class UnauthorizedException extends RuntimeException {

    // Constructor with a generic message
    public UnauthorizedException(String message) {
        super(message);
    }

    // Constructor with action and required role (used in GlobalExceptionHandler)
    public UnauthorizedException(String action, String requiredRole) {
        super(String.format("Unauthorized to perform %s. Required role: %s", action, requiredRole));
    }
}