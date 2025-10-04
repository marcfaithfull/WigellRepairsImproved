package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.entities.Technician;
import com.example.wigellrepairs.repositories.BookingsRepository;
import com.example.wigellrepairs.repositories.ServicesRepository;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import jakarta.transaction.Transactional;

@org.springframework.stereotype.Service
public class AdminServiceImpl implements AdminService {
    BookingsRepository bookingsRepository;
    ServicesRepository servicesRepository;
    TechnicianRepository technicianRepository;

    public AdminServiceImpl(BookingsRepository bookingsRepository, ServicesRepository servicesRepository, TechnicianRepository technicianRepository) {
        this.bookingsRepository = bookingsRepository;
        this.servicesRepository = servicesRepository;
        this.technicianRepository = technicianRepository;
    }

    @Transactional
    @Override
    public void addService(Service service) {
        servicesRepository.save(service);
    }

    @Transactional
    @Override
    public void addTechnician(Technician technician) {
        technician.setWigellRepairsTechnicianId(null);
        technicianRepository.save(technician);
    }
}
