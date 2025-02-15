package com.altern.test.exception;


import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends ApiException {
    public DuplicateResourceException(String resource, String field, Object value) {
        super(HttpStatus.CONFLICT,
                "%s with %s '%s' already exists".formatted(resource, field, value));
    }
}
