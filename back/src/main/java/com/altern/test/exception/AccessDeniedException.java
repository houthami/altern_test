package com.altern.test.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends ApiException {
    public AccessDeniedException() {
        super(HttpStatus.FORBIDDEN,
                "Access denied: You don't have permission to perform this action");
    }
}
