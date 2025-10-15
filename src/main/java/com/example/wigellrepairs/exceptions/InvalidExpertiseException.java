package com.example.wigellrepairs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidExpertiseException extends RuntimeException {
    public InvalidExpertiseException() {
        super("Choose a technician with the correct area of expertise");
    }
}
