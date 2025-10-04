package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.repositories.BookingsRepository;
import com.example.wigellrepairs.repositories.ServicesRepository;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import com.example.wigellrepairs.services.calculators.CurrencyConverter;
import com.example.wigellrepairs.services.calculators.OrderCalculator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private BookingsRepository bookingsRepository;
    private ServicesRepository servicesRepository;
    private TechnicianRepository technicianRepository;
    private CurrencyConverter currencyConverter;
    private OrderCalculator orderCalculator;

    @Autowired
    public CustomerServiceImpl(BookingsRepository bookingsRepository, ServicesRepository servicesRepository, TechnicianRepository technicianRepository, CurrencyConverter currencyConverter, OrderCalculator orderCalculator) {
        this.bookingsRepository = bookingsRepository;
        this.servicesRepository = servicesRepository;
        this.technicianRepository = technicianRepository;
        this.currencyConverter = currencyConverter;
        this.orderCalculator = orderCalculator;
    }

    public int test() {
        int testSek = 1000;
        int testEuro = currencyConverter.convertSekToEuro(testSek);
        return testEuro;
    }

    @Override
    public List<String> listAllServices() {
        return servicesRepository.findAllServiceNames();
    }

    @Override
    public void bookService(Booking booking, Principal principal) {
        booking.setWigellRepairsBookingCustomer(principal.getName());
        orderCalculator.calculateTotalCost(booking);
        bookingsRepository.save(booking);
    }
}
