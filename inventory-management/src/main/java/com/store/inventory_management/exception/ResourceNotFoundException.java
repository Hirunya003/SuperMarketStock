package com.store.inventory_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Returns 404 status code
public class ResourceNotFoundException extends RuntimeException {

    // Constructor with resourceName, fieldName, and fieldValue
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }

    // Constructor with a generic message
    public ResourceNotFoundException(String message) {
        super(message);
    }
}