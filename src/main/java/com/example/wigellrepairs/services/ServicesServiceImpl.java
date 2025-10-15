package com.example.wigellrepairs.services;

import com.example.wigellrepairs.dto.ServiceDto;
import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.repositories.BookingRepository;
import com.example.wigellrepairs.repositories.ServiceRepository;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicesServiceImpl implements ServicesService {
    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;
    private final TechnicianRepository technicianRepository;
    private final Logger SERVICE_LOGGER = LogManager.getLogger(ServicesServiceImpl.class);

    @Autowired
    public ServicesServiceImpl(BookingRepository bookingRepository, ServiceRepository serviceRepository, TechnicianRepository technicianRepository) {
        this.bookingRepository = bookingRepository;
        this.serviceRepository = serviceRepository;
        this.technicianRepository = technicianRepository;
    }

    @Override
    public List<ServiceDto> services() {
        return ServiceDto.serviceDtoList(serviceRepository.findAll());
    }

    @Transactional
    @Override
    public ResponseEntity<String> addService(com.example.wigellrepairs.entities.Service service) {
        if (!technicianRepository.existsById(service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Choose a technician from the database");
        }
        if (!service.getWigellRepairsServiceType().equals("Car") &&
                !service.getWigellRepairsServiceType().equals("White goods") &&
                !service.getWigellRepairsServiceType().equals("Electronics")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("Area of expertise must be Car, White goods or Electronics (Case sensitive)");
        }
        com.example.wigellrepairs.entities.Service serviceToSave = new com.example.wigellrepairs.entities.Service();
        serviceToSave.setWigellRepairsServiceName(service.getWigellRepairsServiceName());
        serviceToSave.setWigellRepairsServiceType(service.getWigellRepairsServiceType());
        serviceToSave.setWigellRepairsServicePrice(service.getWigellRepairsServicePrice());
        Technician technician = technicianRepository.findTechnicianByWigellRepairsTechnicianId(
                service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId());
        if (!technician.getWigellRepairsAreaOfExpertise().equals(service.getWigellRepairsServiceType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose a technician with the correct area of expertise");
        }
        serviceToSave.setWigellRepairsServiceTechnician(technician);
        serviceRepository.save(serviceToSave);
        SERVICE_LOGGER.info("Service: '{}' was added to the database", serviceToSave.getWigellRepairsServiceName());
        return ResponseEntity.status(HttpStatus.OK).body("The service was added successfully");
    }

    @Override
    public ResponseEntity<String> updateService(com.example.wigellrepairs.entities.Service service) {
        if (!serviceRepository.existsById(service.getWigellRepairsServiceId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no service with this id in the database");
        }
        com.example.wigellrepairs.entities.Service serviceToUpdate = serviceRepository.findServiceByWigellRepairsServiceId(service.getWigellRepairsServiceId());
        if (!technicianRepository.existsById(service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no technician with this id in the database");
        }
        Technician technicianToUpdate = technicianRepository.findTechnicianByWigellRepairsTechnicianId(service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId());
        if (!technicianToUpdate.getWigellRepairsAreaOfExpertise().equals(serviceToUpdate.getWigellRepairsServiceType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose a technician with the correct area of expertise");
        }
        serviceToUpdate.setWigellRepairsServiceName(service.getWigellRepairsServiceName());
        serviceToUpdate.setWigellRepairsServicePrice(service.getWigellRepairsServicePrice());
        serviceToUpdate.setWigellRepairsServiceTechnician(technicianToUpdate);
        SERVICE_LOGGER.info("Service with id '{}' was updated", service.getWigellRepairsServiceId());
        serviceRepository.save(serviceToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body("This service has been updated");
    }

    @Transactional
    @Override
    public ResponseEntity<String> remService(Long id) {
        com.example.wigellrepairs.entities.Service serviceToRemove = serviceRepository.findServiceByWigellRepairsServiceId(id);
        if (!serviceRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no service with this id in the database");
        }
        List<Booking> bookings = bookingRepository.findAll();
        List<Booking> bookingsWithMatchingService = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getWigellRepairsBookingService().getWigellRepairsServiceId().equals(serviceToRemove.getWigellRepairsServiceId()) &&
                    booking.getWigellRepairsBookingCancelled().equals(false) &&
                    booking.getWigellRepairsBookingDate().isAfter(LocalDate.now())) {
                bookingsWithMatchingService.add(booking);
            }
        }
        if (!bookingsWithMatchingService.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot remove this service because it has been booked by one or more users");
        }
        serviceRepository.deleteServiceByWigellRepairsServiceId(serviceToRemove.getWigellRepairsServiceId());
        SERVICE_LOGGER.info("Service: '{}' was removed from the database", serviceToRemove.getWigellRepairsServiceId());
        return ResponseEntity.status(HttpStatus.OK).body("The service has been removed");
    }
}
