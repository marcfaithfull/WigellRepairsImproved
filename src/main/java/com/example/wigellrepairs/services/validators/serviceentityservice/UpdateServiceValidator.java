package com.example.wigellrepairs.services.validators.serviceentityservice;

import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.exceptions.InvalidExpertiseException;
import com.example.wigellrepairs.exceptions.ServiceNotFoundException;
import com.example.wigellrepairs.exceptions.TechnicianNotFoundException;
import com.example.wigellrepairs.repositories.ServiceRepository;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateServiceValidator {
    TechnicianRepository technicianRepository;
    ServiceRepository serviceRepository;

    @Autowired
    UpdateServiceValidator(TechnicianRepository technicianRepository, ServiceRepository serviceRepository) {
        this.technicianRepository = technicianRepository;
        this.serviceRepository = serviceRepository;
    }

    public void validateServiceExists(ServiceEntity service) {
        if (!serviceRepository.existsById(service.getWigellRepairsServiceId())) {
            throw new ServiceNotFoundException();
        }
    }

    public void validateTechnicianExists(ServiceEntity service) {
        if (!technicianRepository.existsById(service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId())) {
            throw new TechnicianNotFoundException();
        }
    }

    public void validateTechnicianExpertise(ServiceEntity service) {
        ServiceEntity existingService = serviceRepository.findById(service.getWigellRepairsServiceId())
                .orElseThrow(ServiceNotFoundException::new);

        Technician technicianToUpdate = technicianRepository.findTechnicianByWigellRepairsTechnicianId(
                service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId());

        if (!technicianToUpdate.getWigellRepairsAreaOfExpertise().equals(existingService.getWigellRepairsServiceType())) {
            throw new InvalidExpertiseException();
        }
    }
}
