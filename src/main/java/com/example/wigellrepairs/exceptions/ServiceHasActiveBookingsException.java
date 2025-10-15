package com.example.wigellrepairs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ServiceHasActiveBookingsException extends RuntimeException {
    public ServiceHasActiveBookingsException(String message) {
        super(message);
    }
}
