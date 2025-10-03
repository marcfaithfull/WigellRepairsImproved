package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.repositories.BookingsRepository;
import com.example.wigellrepairs.repositories.ServicesRepository;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
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
    public void addService(ServiceEntity serviceEntity) {
        servicesRepository.save(serviceEntity);
    }
}
