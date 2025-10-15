package com.example.wigellrepairs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException() {
        super("There is no service with this id in the database");
    }
}
