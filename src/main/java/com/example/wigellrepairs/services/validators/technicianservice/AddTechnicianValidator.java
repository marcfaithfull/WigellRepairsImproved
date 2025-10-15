package com.example.wigellrepairs.services.validators.technicianservice;

import com.example.wigellrepairs.exceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AddTechnicianValidator {

    public void validateExpertise(String expertise) throws ValidationException {
        String[] validExpertises = {"Car", "White goods", "Electronics"};
        boolean isValid = Arrays.stream(validExpertises).anyMatch(expertise::equals);
        if (!isValid) {
            throw new ValidationException(
                    "Area of expertise must be: " +
                            String.join(", ", validExpertises) + " (Case sensitive)");
        }
    }
}
