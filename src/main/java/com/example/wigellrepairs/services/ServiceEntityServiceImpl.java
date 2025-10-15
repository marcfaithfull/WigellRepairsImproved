package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.exceptions.*;
import com.example.wigellrepairs.repositories.ServiceRepository;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import com.example.wigellrepairs.services.validators.serviceentityservice.AddServiceValidator;
import com.example.wigellrepairs.services.validators.serviceentityservice.RemServiceValidator;
import com.example.wigellrepairs.services.validators.serviceentityservice.UpdateServiceValidator;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ServiceEntityServiceImpl implements ServiceEntityService {
    private final ServiceRepository serviceRepository;
    private final TechnicianRepository technicianRepository;
    private final AddServiceValidator addServiceValidator;
    private final UpdateServiceValidator updateServiceValidator;
    private final RemServiceValidator remServiceValidator;
    private final Logger SERVICE_LOGGER = LogManager.getLogger(ServiceEntityServiceImpl.class);

    @Autowired
    public ServiceEntityServiceImpl(ServiceRepository serviceRepository,
                                    TechnicianRepository technicianRepository, AddServiceValidator addServiceValidator,
                                    UpdateServiceValidator updateServiceValidator,  RemServiceValidator remServiceValidator) {
        this.serviceRepository = serviceRepository;
        this.technicianRepository = technicianRepository;
        this.addServiceValidator = addServiceValidator;
        this.updateServiceValidator = updateServiceValidator;
        this.remServiceValidator = remServiceValidator;
    }

    @Override
    public List<ServiceDto> getServices() {
        return ServiceDto.serviceDtoList(serviceRepository.findAll());
    }

    @Override
    public void addService(ServiceEntity service) throws TechnicianNotFoundException, InvalidExpertiseException {
        addServiceValidator.validateTechnicianExists(service);
        addServiceValidator.validateServiceType(service);
        addServiceValidator.validateTechnicianExpertise(service);

        ServiceEntity serviceToSave = new ServiceEntity();
        serviceToSave.setWigellRepairsServiceName(service.getWigellRepairsServiceName());
        serviceToSave.setWigellRepairsServiceType(service.getWigellRepairsServiceType());
        serviceToSave.setWigellRepairsServicePrice(service.getWigellRepairsServicePrice());
        Technician technician = technicianRepository.findTechnicianByWigellRepairsTechnicianId(
                service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId());
        serviceToSave.setWigellRepairsServiceTechnician(technician);

        serviceRepository.save(serviceToSave);
        SERVICE_LOGGER.info("Service: '{}' was added to the database", serviceToSave.getWigellRepairsServiceName());
    }

    @Override
    public void updateService(ServiceEntity service) throws ServiceNotFoundException, TechnicianNotFoundException, InvalidExpertiseException {
        updateServiceValidator.validateServiceExists(service);
        updateServiceValidator.validateTechnicianExists(service);
        updateServiceValidator.validateTechnicianExpertise(service);

        ServiceEntity serviceToUpdate = serviceRepository.findById(service.getWigellRepairsServiceId())
                .orElseThrow(ServiceNotFoundException::new);

        serviceToUpdate.setWigellRepairsServiceName(service.getWigellRepairsServiceName());
        serviceToUpdate.setWigellRepairsServicePrice(service.getWigellRepairsServicePrice());
        serviceToUpdate.setWigellRepairsServiceTechnician(service.getWigellRepairsServiceTechnician());

        serviceRepository.save(serviceToUpdate);
        SERVICE_LOGGER.info("Service with id '{}' was updated", service.getWigellRepairsServiceId());
    }

    @Override
    public void remService(Long id) throws ServiceNotFoundException, ServiceHasActiveBookingsException {
        remServiceValidator.validateServiceExists(id);

        ServiceEntity serviceToRemove = serviceRepository.findServiceByWigellRepairsServiceId(id);

        remServiceValidator.validateNoActiveBookings(serviceToRemove);

        serviceRepository.deleteServiceByWigellRepairsServiceId(id);
        SERVICE_LOGGER.info("Service: '{}' was removed from the database", id);
    }
}
