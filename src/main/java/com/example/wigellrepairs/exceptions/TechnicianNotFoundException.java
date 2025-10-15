package com.example.wigellrepairs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TechnicianNotFoundException extends RuntimeException {
    public TechnicianNotFoundException() {
        super("There is no technician with this id in the database");
    }
}
