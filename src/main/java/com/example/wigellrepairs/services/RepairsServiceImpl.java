package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.repositories.BookingsRepository;
import com.example.wigellrepairs.repositories.ServicesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RepairsServiceImpl implements RepairsService {
    private BookingsRepository bookingsRepository;
    private ServicesRepository servicesRepository;

    public RepairsServiceImpl(BookingsRepository bookingsRepository, ServicesRepository servicesRepository) {
        this.bookingsRepository = bookingsRepository;
        this.servicesRepository = servicesRepository;
    }

    // LIST ALL SERVICES
    @Override
    public List<ServiceEntity> listAllServices() {
        return servicesRepository.findAll();
    }
}
