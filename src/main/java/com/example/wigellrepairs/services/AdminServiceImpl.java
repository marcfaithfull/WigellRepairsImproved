package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.repositories.BookingsRepository;
import com.example.wigellrepairs.repositories.ServicesRepository;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class AdminServiceImpl implements AdminService {
    BookingsRepository bookingsRepository;
    ServicesRepository servicesRepository;
    TechnicianRepository technicianRepository;
    private static final Logger ADMIN_SERVICE_LOGGER = LogManager.getLogger(AdminServiceImpl.class);

    public AdminServiceImpl(BookingsRepository bookingsRepository, ServicesRepository servicesRepository, TechnicianRepository technicianRepository) {
        this.bookingsRepository = bookingsRepository;
        this.servicesRepository = servicesRepository;
        this.technicianRepository = technicianRepository;
    }

    @Override
    public List<Booking> listCancelled() {
        List<Booking> allBookings = bookingsRepository.findAll();
        List<Booking> cancelledBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getWigellRepairsBookingCancelled()) {
                cancelledBookings.add(booking);
            }
        }
        return cancelledBookings;
    }

    @Override
    public List<Booking> listUpcoming() {
        List<Booking> allBookings = bookingsRepository.findAll();
        List<Booking> upcomingBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getWigellRepairsBookingDate().isAfter(LocalDate.now())) {
                upcomingBookings.add(booking);
            }
        }
        return upcomingBookings;
    }

    @Override
    public List<Booking> listPast() {
        List<Booking> allBookings = bookingsRepository.findAll();
        List<Booking> pastBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getWigellRepairsBookingDate().isBefore(LocalDate.now())) {
                pastBookings.add(booking);
            }
        }
        return pastBookings;
    }

    @Transactional
    @Override
    public void addService(Service service) {
        servicesRepository.save(service);
        ADMIN_SERVICE_LOGGER.info("Service: {} has been added to the database", service.getWigellRepairsServiceName());
    }

    @Override
    public void updateService(Service service) {
        Service existingService = servicesRepository.findById(service.getWigellRepairsServiceId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        Technician existingTechnician = technicianRepository.findById(service.getWigellRepairsServiceTechnician().getWigellRepairsTechnicianId())
                .orElseThrow(() -> new EntityNotFoundException("Technician not found"));

        existingService.setWigellRepairsServiceName(service.getWigellRepairsServiceName());
        existingService.setWigellRepairsServiceType(service.getWigellRepairsServiceType());
        existingService.setWigellRepairsServicePrice(service.getWigellRepairsServicePrice());
        existingService.setWigellRepairsServiceTechnician(existingTechnician);

        servicesRepository.save(existingService);

    }

    @Transactional
    @Override
    public void remService(Long id) {
        servicesRepository.deleteServiceByWigellRepairsServiceId(id);
    }

    @Transactional
    @Override
    public ResponseEntity<String> addTechnician(Technician technician) {
        technician.setWigellRepairsTechnicianId(null);
        String expertise = technician.getWigellRepairsAreaOfExpertise();
        if (!expertise.equals("Car") && !expertise.equals("White goods") && !expertise.equals("Electronics")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Area of expertise must be Car, White goods or Electronics (Case sensitive)");
        }
        technicianRepository.save(technician);

        return ResponseEntity.status(HttpStatus.CREATED).body("Technician added successfully");
    }

    public List<Technician> technicians() {
        return technicianRepository.findAll();
    }
}
