package com.example.wigellrepairs.services;

import com.example.wigellrepairs.entities.Booking;
import com.example.wigellrepairs.entities.Service;
import com.example.wigellrepairs.repositories.BookingsRepository;
import com.example.wigellrepairs.repositories.ServicesRepository;
import com.example.wigellrepairs.repositories.TechnicianRepository;
import com.example.wigellrepairs.services.calculators.CurrencyConverter;
import com.example.wigellrepairs.services.calculators.OrderCalculator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private BookingsRepository bookingsRepository;
    private ServicesRepository servicesRepository;
    private TechnicianRepository technicianRepository;
    private CurrencyConverter currencyConverter;
    private OrderCalculator orderCalculator;
    private static final Logger CUSTOMER_SERVICE_LOGGER = LogManager.getLogger(CustomerServiceImpl.class);

    @Autowired
    public CustomerServiceImpl(BookingsRepository bookingsRepository, ServicesRepository servicesRepository,
                               TechnicianRepository technicianRepository, CurrencyConverter currencyConverter,
                               OrderCalculator orderCalculator) {
        this.bookingsRepository = bookingsRepository;
        this.servicesRepository = servicesRepository;
        this.technicianRepository = technicianRepository;
        this.currencyConverter = currencyConverter;
        this.orderCalculator = orderCalculator;
    }

    @Override
    public List<Service> services() {
        return servicesRepository.findAll();
    }

    @Override
    public void bookService(Booking booking, Principal principal) {
        booking.setWigellRepairsBookingCustomer(principal.getName());
        Long serviceId = booking.getWigellRepairsBookingService().getWigellRepairsServiceId();
        Service serviceToBook = servicesRepository.findServiceByWigellRepairsServiceId(serviceId);
        booking.setWigellRepairsBookingTotalPrice(serviceToBook.getWigellRepairsServicePrice());
        //orderCalculator.calculateTotalCost(booking, servicesRepository.findServiceByWigellRepairsServiceId(booking.getWigellRepairsBookingService().getWigellRepairsServiceId()));
        currencyConverter.convertSekToEuro(booking);
        bookingsRepository.save(booking);
        // Logging
        CUSTOMER_SERVICE_LOGGER.info("{} booked service with id:{}",
                booking.getWigellRepairsBookingCustomer(), booking.getWigellRepairsBookingService().getWigellRepairsServiceId());
    }

    @Override
    public void cancelBooking(Booking booking, Principal principal) {
        Booking bookingToCancel = bookingsRepository.findById(booking.getWigellRepairsBookingId())
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        if (!bookingToCancel.getWigellRepairsBookingCustomer().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorised to cancel this booking");
        }

        bookingToCancel.setWigellRepairsBookingCancelled(true);
        bookingsRepository.save(bookingToCancel);
    }

    @Override
    public List<Booking> myBookings(Principal principal) {
        List<Booking> allBookings = bookingsRepository.findAll();
        List<Booking> myBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getWigellRepairsBookingCustomer().equals(principal.getName()) && (booking.getWigellRepairsBookingCancelled().equals(false))) {
                myBookings.add(booking);
            }
        }
        return myBookings;
    }
}
