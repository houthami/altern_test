package com.altern.test.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends ApiException {
    public AuthException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
