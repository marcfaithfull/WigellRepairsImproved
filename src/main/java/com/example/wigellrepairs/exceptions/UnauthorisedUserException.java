package com.example.wigellrepairs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorisedUserException extends RuntimeException {
    public UnauthorisedUserException(String message) {
        super(message);
    }
}
