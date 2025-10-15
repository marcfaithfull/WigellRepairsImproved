package com.example.wigellrepairs.services.validators.serviceentityservice;

import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.exceptions.InvalidExpertiseException;
import com.example.wigellrepairs.exceptions.InvalidServiceTypeException;
import com.example.wigellrepairs.exceptions.TechnicianNotFoundException;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddServiceValidator {
    TechnicianRepository technicianRepository;

    @Autowired
    AddServiceValidator(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    public void validateTechnicianExists(ServiceEntity service) {
        if (!technicianRepository.existsById(
                service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId())) {
            throw new TechnicianNotFoundException();
        }
    }

    public void validateServiceType(ServiceEntity service) {
        if (!isValidServiceType(service.getWigellRepairsServiceType())) {
            throw new InvalidServiceTypeException(
                    "Area of expertise must be Car, White goods or Electronics (Case sensitive)");
        }
    }

    private boolean isValidServiceType(String type) {
        return type.equals("Car") || type.equals("White goods") || type.equals("Electronics");
    }

    public void validateTechnicianExpertise(ServiceEntity service) {
        Technician technician = technicianRepository.findTechnicianByWigellRepairsTechnicianId(
                service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId());

        if (!technician.getWigellRepairsAreaOfExpertise().equals(service.getWigellRepairsServiceType())) {
            throw new InvalidExpertiseException();
        }
    }
}
