package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.BookingsEntity;
import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.repositories.BookingsRepository;
import com.example.wigellrepairs.repositories.ServicesRepository;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import com.example.wigellrepairs.services.calculators.CurrencyConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private BookingsRepository bookingsRepository;
    private ServicesRepository servicesRepository;
    private TechnicianRepository technicianRepository;
    private CurrencyConverter currencyConverter;

    @Autowired
    public CustomerServiceImpl(BookingsRepository bookingsRepository, ServicesRepository servicesRepository, TechnicianRepository technicianRepository, CurrencyConverter currencyConverter) {
        this.bookingsRepository = bookingsRepository;
        this.servicesRepository = servicesRepository;
        this.technicianRepository = technicianRepository;
        this.currencyConverter = currencyConverter;
    }

    public int test() {
        int testSek = 1000;
        int testEuro = currencyConverter.convertSekToEuro(testSek);
        return testEuro;
    }

    @Override
    public List<ServiceEntity> listAllServices() {
        return servicesRepository.findAll();
    }

    @Override
    public void bookService(BookingsEntity bookingsEntity) {

    }
}
