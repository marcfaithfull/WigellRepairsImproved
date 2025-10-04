package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.repositories.BookingsRepository;
import com.example.wigellrepairs.repositories.ServicesRepository;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class AdminServiceImpl implements AdminService {
    BookingsRepository bookingsRepository;
    ServicesRepository servicesRepository;
    TechnicianRepository technicianRepository;

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
            if (booking.isCancelled()) {
                cancelledBookings.add(booking);
            }
        }
        return cancelledBookings;
    }

    @Override
    public List<Booking> listUpcoming() {
        return List.of();
    }

    @Override
    public List<Booking> listPast() {
        return List.of();
    }

    @Transactional
    @Override
    public void addService(Service service) {
        servicesRepository.save(service);
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
    public void addTechnician(Technician technician) {
        technician.setWigellRepairsTechnicianId(null);
        technicianRepository.save(technician);
    }

    public List<String> technicians() {
        return technicianRepository.findAllTechnicianNames();
    }
}
