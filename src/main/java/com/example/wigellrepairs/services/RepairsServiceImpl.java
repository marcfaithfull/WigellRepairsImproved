package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.ServiceEntity;
import com.example.wigellrepairs.repositories.BookingsRepository;
import com.example.wigellrepairs.repositories.ServicesRepository;
import com.example.wigellrepairs.services.calculators.CurrencyConverter;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RepairsServiceImpl implements RepairsService {
    private BookingsRepository bookingsRepository;
    private ServicesRepository servicesRepository;
    private CurrencyConverter currencyConverter;

    public RepairsServiceImpl(BookingsRepository bookingsRepository, ServicesRepository servicesRepository, CurrencyConverter currencyConverter) {
        this.bookingsRepository = bookingsRepository;
        this.servicesRepository = servicesRepository;
        this.currencyConverter = currencyConverter;
    }

    public int test() {
        int testSek = 1000;
        int testEuro = currencyConverter.convertSekToEuro(testSek);
        return testEuro;
    }

    // LIST ALL SERVICES
    @Override
    public List<ServiceEntity> listAllServices() {
        return servicesRepository.findAll();
    }
}
